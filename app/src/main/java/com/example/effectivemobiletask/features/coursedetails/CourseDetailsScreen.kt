package com.example.effectivemobiletask.features.coursedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.ui.theme.EffectiveMobileTaskTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CourseDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: CourseDetailsViewModel =
        hiltViewModel(), // Получаем VM через Hilt.
) {
    val state by
        viewModel.uiState
            .collectAsStateWithLifecycle() // Подписка на стейт с учётом
    // жизненного цикла.

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(
            rate = state.course?.rate.toString(), // Отображаем рейтинг курса.
            publishDate =
                state.course
                    ?.publishDate
                    ?.format(
                        DateTimeFormatter.ofPattern("MMMM d, yyyy")
                    ), // Форматируем дату публикации.
        )

        Text(
            text = state.course?.title.toString(), // Заголовок курса.
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
    }
}

@Composable
fun TopSection(
    rate: String = "9.9", // Значение рейтинга по умолчанию.
    publishDate: String? = null, // Дата публикации, может быть null.
    image: Int =
        R.drawable.course_picture_default, // Картинка курса по умолчанию.
) {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Image(
            painter =
                painterResource(
                    R.drawable.course_picture_default
                ), // Фон-картинка курса.
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                IconButton(
                    onClick = {}, // Кнопка "назад".
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                    modifier = Modifier.align(Alignment.TopStart),
                ) {
                    Icon(
                        painterResource(
                            R.drawable.outline_arrow_back_ios_new_24
                        ),
                        contentDescription = null,
                    )
                }

                IconButton(
                    onClick = {}, // Кнопка "закладка".
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    Icon(
                        painterResource(R.drawable.bookmark),
                        contentDescription = null,
                    )
                }
            }
        }

        Row(
            modifier =
                Modifier.align(
                    Alignment.BottomStart
                ), // Нижняя панель с рейтингом и датой.
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier =
                    Modifier.background(
                            color =
                                Color(
                                    red = 1f,
                                    green = 1f,
                                    blue = 1f,
                                    alpha = 0.8f,
                                    colorSpace = ColorSpaces.Srgb,
                                ),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // Блок с рейтингом.
                Icon(
                    painterResource(R.drawable.star_fill),
                    contentDescription = null,
                )

                Text(text = rate, style = MaterialTheme.typography.bodySmall)
            }
            Row(
                modifier =
                    Modifier.background(
                            color =
                                Color(
                                    red = 1f,
                                    green = 1f,
                                    blue = 1f,
                                    alpha = 0.8f,
                                    colorSpace = ColorSpaces.Srgb,
                                ),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = publishDate.toString(), // Блок с датой публикации.
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
fun MyPreview() {
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
        Box(modifier = Modifier.fillMaxSize()) { TopSection() }
    }
}
