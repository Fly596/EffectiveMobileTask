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
    ASC("ascending"), DESC("descending"), NONE("none")
}

data class MainUiState(
    val courses: List<Course> = emptyList(),
    val sortingOption: String = SortingOptions.NONE.option,
    val isLoading: Boolean = false,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CourseRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCourses()
    }

    fun fetchCourses() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.refreshCourses()

            repository.fetchCourses().collect { courses ->
                _uiState.update { it.copy(courses = courses) }
                _uiState.update { it.copy(isLoading = false) }

            }

        }

    }


}
