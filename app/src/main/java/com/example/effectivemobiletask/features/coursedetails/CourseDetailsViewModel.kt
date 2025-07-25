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

data class DetailsUiState(
    val course: Course? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class CourseDetailsViewModel
@Inject
constructor(
    private val repository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val courseId: Int? =
        savedStateHandle.toRoute<Destinations.CourseInfo>().courseId

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (courseId != null) {
            getCourse(courseId)
        }
        /*        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val args =
                        savedStateHandle.toRoute<Destinations.CourseInfo>()
                    val courseID = args.courseId
                    getCourse(courseID)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }*/
    }

    private fun getCourse(id: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.getCourseById(id).let { course ->
                if (course != null) {
                    _uiState.update {
                        it.copy(course = course, isLoading = false)
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
            /*    try {
                val character = repository.getCourseById(id)
                _uiState.update { it.copy(course = character) }
            } catch (e: Exception) {
                e.printStackTrace()
            }*/
        }
    }


}
