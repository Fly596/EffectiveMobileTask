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
    // Колбэк для обработки клика по карточке курса, передает ID курса.
    onCourseClick: (Int) -> Unit,
    // Получение ViewModel с помощью Hilt.
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    // Подписка на состояние UI из ViewModel, жизненно-цикло-безопасным способом.
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Основной контейнер экрана.
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        // Ленивый список для эффективного отображения большого количества элементов.
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            // Создание элементов списка на основе данных из состояния.
            items(items = state.favoritesCourses) { item ->
                // Переиспользуемый компонент для отображения карточки курса.
                CourseCard(
                    item,
                    // Передача функции для обновления статуса "избранное".
                    onAddToFavorite = viewModel::updateCourseBookmark,
                    // Передача колбэка для обработки клика.
                    onCardClick = onCourseClick,
                )
            }
        }

    }
}
