package com.example.effectivemobiletask.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class SortingOptions(val option: String) {
    ASC("ascending"),
    DESC("descending"),
    NONE("none"),
}

data class MainUiState(
    val courses: List<Course> = emptyList(),
    val sortingOption: String = SortingOptions.NONE.option,
    val ascSorting: Boolean? = null,
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
            repository.fetchCourses().collect { couses ->
                if (couses.isEmpty()) {
                    repository.refreshCourses()
                } else {
                    _uiState.update { it.copy(courses = couses) }
                }

                _uiState.update { it.copy(isLoading = false) }
            }
            /*    repository.refreshCourses()

            repository.fetchCourses().collect { courses ->
                _uiState.update { it.copy(courses = courses) }
                _uiState.update { it.copy(isLoading = false) }

            }*/

        }
    }

    fun sortCourses() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository
                .fetchCourses(isAsc = _uiState.value.ascSorting)
                .collect { course ->
                    _uiState.update { it.copy(courses = course) }
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    fun updateSortingOption(input: Boolean?) {
        _uiState.update { it.copy(ascSorting = input) }
    }
}
