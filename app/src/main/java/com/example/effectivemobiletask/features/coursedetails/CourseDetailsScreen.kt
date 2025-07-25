package com.example.effectivemobiletask.features.coursedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R
import java.time.format.DateTimeFormatter

@Composable
fun CourseDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: CourseDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxWidth()) {
        TopSection(
            rate = state.course?.rate.toString(),
            publishDate =
                state.course
                    ?.publishDate
                    ?.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")),
        )
    }

}

@Composable
fun TopSection(
    rate: String = "9.9",
    publishDate: String? ,
    image: Int = R.drawable.course_picture_default,
) {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Image(
            painter = painterResource(R.drawable.course_picture_default),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier

        )
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {}) { }
            Button(onClick = {}) { }
        }
        Row(modifier = Modifier.fillMaxSize(),verticalAlignment = Alignment.Bottom){
            Text(rate, modifier = Modifier)

        }
    }
}
