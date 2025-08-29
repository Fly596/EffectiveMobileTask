package com.example.effectivemobiletask.features.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.ui.components.CourseCard
import com.example.effectivemobiletask.ui.theme.EffectiveMobileTaskTheme
import java.time.LocalDate

@Composable
fun MainScreen(
    // Колбэк для навигации на экран деталей курса.
    onCourseClick: (Int) -> Unit,
    // Получение ViewModel с помощью Hilt.
    viewModel: MainViewModel = hiltViewModel(),
) {
    // Подписка на состояние UI из ViewModel.
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Основной вертикальный контейнер экрана.
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Компонент для поиска и фильтрации (реализация не показана).
        SearchAndFilter(modifier = Modifier.fillMaxWidth())

        // Ряд для отображения и управления сортировкой.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "По дате добавления",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            // Иконка для изменения направления сортировки.
            Icon(
                painter = painterResource(R.drawable.outline_swap_vert_24),
                contentDescription = "sort",
                tint = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier.clickable(
                        onClick = {
                            // При клике обновляем направление сортировки и
                            // запускаем саму сортировку.
                            viewModel.updateSortingOption(!state.isAsc)
                            viewModel.sortCourses()
                        }
                    ),
            )
        }

        // Ленивый список для отображения курсов.
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = state.courses) { item ->
                // Карточка для отображения одного курса.
                CourseCard(
                    item,
                    onAddToFavorite = viewModel::updateCourseBookmark,
                    onCardClick = onCourseClick,
                )
            }
        }

        // Отображение экрана загрузки, если данные загружаются.
        if (state.isLoading) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    val tempCourse: Course =
        Course(
            1,
            "Java-разработчик с нуля",
            "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
            123.5,
            56.0f,
            LocalDate.now(),
            false,
            LocalDate.now(),
        )
    EffectiveMobileTaskTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CourseCard(tempCourse, {}, {})
        }
    }
}

// Экран загрузки.
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.width(64.dp))
    }
}

// Компонент для поиска и фильтрации.
@Composable
fun SearchAndFilter(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Текстовое поле для ввода текста поиска.
        OutlinedTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.outline_search_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            label = { Text("Search courses...") },
            singleLine = true,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(30.dp),
        )

        // Кнопка для фильтрации.
        IconButton(onClick = {}, modifier = Modifier, enabled = false) {
            Icon(
                painter = painterResource(R.drawable.outline_filter_alt_24),
                contentDescription = "filter",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
