package com.example.effectivemobiletask.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.ui.theme.EffectiveMobileTaskTheme
import java.time.LocalDate

@Composable
fun MainScreen(
    onCourseClick: (Int) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Поиск и фильтрация.
        SearchAndFilter(modifier = Modifier.fillMaxWidth())

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
            Icon(
                painter = painterResource(R.drawable.arrow_down_up),
                contentDescription = "sort",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = {
                    viewModel.updateSortingOption(true)

                    viewModel.sortCourses()
                    // ! фильтрация
                })
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = state.courses) { item ->
                CourseCard(
                    item,
                    onAddToFavorite = {},
                    onCardClick = { onCourseClick(it) },
                )
            }
        }

        if (state.isLoading) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun CourseCard(
    course: Course,
    onAddToFavorite: () -> Unit,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable(onClick = { onCardClick(course.id) }),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            CourseCardTopSection(
                onAddToFavorite = onAddToFavorite
                // modifier = Modifier.weight(0.5f),
            )
            CourseCardBottomSection(
                title = course.title,
                text = course.text,
                price = course.price.toString(),
                // modifier = Modifier.weight(0.5f),
            )
            Row(
                modifier =
                    Modifier.padding(horizontal = 16.dp)
                        .clickable(onClick = { { onCardClick(course.id) } }),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    "Подробнее",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Icon(
                    painter =
                        painterResource(R.drawable.arrow_right_short_fill),
                    tint =
                        MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun CourseCardTopSection(
    rate: String = "6.6",
    publishDate: String = "Publish date",
    modifier: Modifier = Modifier,
    onAddToFavorite: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.course_picture_default),
            contentDescription = "course picture",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier =
                Modifier.fillMaxWidth()
                    .height(114.dp)
                    .clip(RoundedCornerShape(12.dp)),
        )
        Row(modifier = Modifier) {
            Row() {
                // Rate.
                Icon(
                    painterResource(R.drawable.star_fill),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun CourseCardBottomSection(
    title: String = "",
    text: String = "text",
    price: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text(text = "$price ₽", style = MaterialTheme.typography.titleMedium)
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

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.width(64.dp))
    }
}

@Composable
fun SearchAndFilter(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                )
            },
            label = { Text("Search courses...") },
            singleLine = true,
            modifier = Modifier,
            shape = RoundedCornerShape(30.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor =
                        MaterialTheme.colorScheme.surfaceBright,
                ),
        )

        IconButton(onClick = {}, modifier = Modifier, enabled = false) {
            Icon(
                painter = painterResource(R.drawable.funnel),
                contentDescription = "filter",
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    title: String? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPassword: Boolean = false,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (title != null) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }
}
