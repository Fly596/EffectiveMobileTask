package com.example.effectivemobiletask.features.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.ui.components.CourseCard

@Composable
fun FavoritesScreen(
    onCourseClick: (Int) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = state.favoritesCourses) { item ->
                CourseCard(
                    item,
                    onAddToFavorite = viewModel::updateCourseBookmark,
                    onCardClick = onCourseClick,
                )
            }
        }

    }
}
