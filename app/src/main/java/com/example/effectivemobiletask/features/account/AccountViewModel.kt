package com.example.effectivemobiletask.features.account

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.effectivemobiletask.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
@Inject
constructor(
    private val repository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

}
