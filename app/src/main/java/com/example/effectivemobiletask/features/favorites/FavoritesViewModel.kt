package com.example.effectivemobiletask.features.favorites

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

// Класс данных, описывающий состояние UI для экрана "Избранное".
data class FavoritesUiState(
    // Список курсов, добавленных в избранное.
    val favoritesCourses: List<Course> = emptyList(),
    // Флаг, указывающий на процесс загрузки данных.
    val isLoading: Boolean = false,
)

// ViewModel для экрана "Избранное".
@HiltViewModel
class FavoritesViewModel
@Inject
constructor(private val repository: CourseRepository) : ViewModel() {

    // Приватное, изменяемое состояние.
    private val _uiState = MutableStateFlow(FavoritesUiState())
    // Публичное, неизменяемое состояние для подписки из UI.
    val uiState = _uiState.asStateFlow()

    // Блок инициализации, вызывается при создании ViewModel.
    init {
        fetchCourses()
    }

    // Функция для получения списка избранных курсов.
    private fun fetchCourses() {
        // Устанавливаем флаг загрузки в true.
        _uiState.update { it.copy(isLoading = true) }

        // Запускаем корутину в ViewModelScope.
        viewModelScope.launch {

            // Подписываемся на Flow из репозитория для получения обновлений в
            // реальном времени.
            repository.getFavoritesCourses().collect { courses ->
                // Обновляем состояние новым списком курсов.
                _uiState.update { it.copy(favoritesCourses = courses) }

                // Устанавливаем флаг загрузки в false после получения данных.
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    // Функция для обновления статуса "избранное" у курса.
    fun updateCourseBookmark(id: Int) {
        // Запускаем асинхронную операцию для изменения данных в репозитории.
        viewModelScope.launch { repository.setLike(id) }
    }
}
