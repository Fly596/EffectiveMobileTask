package com.example.effectivemobiletask.features.coursedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.repository.CourseRepository
import com.example.effectivemobiletask.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Класс данных, представляющий состояние экрана.
data class DetailsUiState(
    val course: Course? = null,
    val isLoading: Boolean = false,
)

// ViewModel экрана с деталями курса.
@HiltViewModel
class CourseDetailsViewModel
@Inject
constructor(
    // Внедрение репозитория для получения данных.
    private val repository: CourseRepository,
    // Получение аргументов навигации.
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // Извлечение ID курса из аргументов навигации.
    private val courseId: Int? =
        savedStateHandle.toRoute<Destinations.CourseInfo>().courseId

    // Приватное, изменяемое состояние.
    private val _uiState = MutableStateFlow(DetailsUiState())
    // Публичное, неизменяемое состояние для UI.
    val uiState = _uiState.asStateFlow()

    // Блок инициализации, вызывается при создании ViewModel.
    init {
        if (courseId != null) {
            getCourse(courseId)
        }
    }

    // Функция для загрузки данных о курсе.
    private fun getCourse(id: Int) {
        // Обновляем состояние, показывая индикатор загрузки.
        _uiState.update { it.copy(isLoading = true) }

        // Запускаем корутину для асинхронной загрузки данных.
        viewModelScope.launch {
            repository.getCourseById(id).let { course ->
                if (course != null) {
                    // Если курс найден, обновляем состояние с данными курса.
                    _uiState.update {
                        it.copy(course = course, isLoading = false)
                    }
                } else {
                    // Если курс не найден, просто убираем индикатор загрузки.
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
