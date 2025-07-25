package com.example.effectivemobiletask.features.coursedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.repository.CourseRepository
import com.example.effectivemobiletask.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class DetailsUiState(val character: Course? = null)

@HiltViewModel
class CourseDetailsViewModel
@Inject
constructor(
    private val repository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
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
        }
    }

    private fun getCourse(id: Int) {
        viewModelScope.launch {
            try {
                val character = repository.getCourseById(id)
                _uiState.update { it.copy(character = character) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
