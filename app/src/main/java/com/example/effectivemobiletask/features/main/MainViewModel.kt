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

enum class SortingOptions(val option: String) {
    ASC("ascending"),
    DESC("descending"),
    NONE("none"),
}

data class MainUiState(
    val courses: List<Course> = emptyList(),
    val sortingOption: String = SortingOptions.NONE.option,
    val isAsc: Boolean = false,
    val isLoading: Boolean = false,
)

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: CourseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCourses()
    }

    private fun fetchCourses() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.fetchCourses().collect { courses ->
                if (courses.isEmpty()) {
                    repository.refreshCourses()

                } else {

                    _uiState.update { it.copy(courses = courses) }
                }

                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun sortCourses() {

        viewModelScope.launch {
            repository.fetchCourses(isAsc = _uiState.value.isAsc).collect {
                course ->
                _uiState.update { it.copy(courses = course) }
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateSortingOption(input: Boolean) {

        _uiState.update { it.copy(isAsc = input) }
    }

    fun updateCourseBookmark(id: Int) {
        viewModelScope.launch { repository.setLike(id) }
    }
}
