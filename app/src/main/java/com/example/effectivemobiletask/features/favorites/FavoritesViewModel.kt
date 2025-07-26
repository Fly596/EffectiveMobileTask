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

data class FavoritesUiState(
    val favoritesCourses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: CourseRepository):
    ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCourses()
    }

    private fun fetchCourses() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            //repository.refreshCourses()

            repository.getFavoritesCourses().collect { couses ->
                _uiState.update { it.copy(favoritesCourses = couses) }

                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateCourseBookmark(id: Int) {
        viewModelScope.launch { repository.setLike(id) }
    }
}
