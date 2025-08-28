package com.example.effectivemobiletask.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Перечисление для возможных вариантов сортировки.
enum class SortingOptions(val option: String) {
    ASC("ascending"),
    DESC("descending"),
    NONE("none"),
}

// Класс данных, представляющий состояние UI главного экрана.
data class MainUiState(
    // Список курсов для отображения.
    val courses: List<Course> = emptyList(),
    // Текущий выбранный вариант сортировки.
    val sortingOption: String = SortingOptions.NONE.option,
    // Флаг для направления сортировки (по возрастанию/убыванию).
    val isAsc: Boolean = false,
    // Флаг состояния загрузки.
    val isLoading: Boolean = false,
)

// ViewModel для главного экрана.
@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: CourseRepository) : ViewModel() {

    // Приватное, изменяемое состояние.
    private val _uiState = MutableStateFlow(MainUiState())
    // Публичное, неизменяемое состояние для UI.
    val uiState = _uiState.asStateFlow()

    // При создании ViewModel, запускаем загрузку курсов.
    init {
        fetchCourses()
    }

    // Функция для получения курсов из репозитория.
    private fun fetchCourses() {
        // Устанавливаем флаг загрузки.
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            // Подписываемся на Flow курсов из репозитория.
            repository.fetchCourses().collect { courses ->
                // Если локальная база данных пуста, запрашиваем обновление с
                // сервера.
                if (courses.isEmpty()) {
                    repository.refreshCourses()
                } else {
                    // Иначе обновляем состояние полученными курсами.
                    _uiState.update { it.copy(courses = courses) }
                }
                // Снимаем флаг загрузки.
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    // Функция для сортировки списка курсов.
    fun sortCourses() {
        viewModelScope.launch {
            // Запрашиваем курсы из репозитория с указанием направления
            // сортировки.
            repository.fetchCourses(isAsc = _uiState.value.isAsc).collect {
                course ->
                // Обновляем состояние отсортированным списком.
                _uiState.update { it.copy(courses = course) }
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    // Обновляет флаг направления сортировки в состоянии.
    fun updateSortingOption(input: Boolean) {
        _uiState.update { it.copy(isAsc = input) }
    }

    // Обновляет статус "избранное" для курса.
    fun updateCourseBookmark(id: Int) {
        viewModelScope.launch { repository.setLike(id) }
    }
}
