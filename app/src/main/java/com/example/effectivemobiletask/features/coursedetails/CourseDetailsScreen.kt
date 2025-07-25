package com.example.effectivemobiletask.features.coursedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R

@Composable
fun CourseDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: CourseDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    TopSection()
}

@Composable
fun TopSection(rate: String,
               publishDate: String,
               image: Int = R.drawable.course_picture_default ) {
    Box(modifier = Modifier) {
        Image(
            painter = painterResource(R.drawable.course_picture_default),
            contentDescription = null,
        )
    }
}
