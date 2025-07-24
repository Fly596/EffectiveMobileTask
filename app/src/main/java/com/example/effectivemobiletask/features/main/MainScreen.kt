package com.example.effectivemobiletask.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R

@Composable
fun MainScreen(
    onCourseClick: (Int) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    //! Поле поиска и кнопка фильтрации. Сортировка по дате.
    //! LazyList с карточками курсов.

    Column(modifier = Modifier.fillMaxWidth()) {
        // Поиск и фильтрация.
        SearchAndFilter()

        LazyColumn{
            items(items = state.courses){item ->
                Text(text = item.title)
            }
        }

    }

}

@Composable
fun SearchAndFilter() {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = "",
            label = { Text("Search courses...") },
            onValueChange = { },
            modifier = Modifier,
            shape = RoundedCornerShape(30.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor =
                        MaterialTheme.colorScheme.surfaceBright,
                ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "search",
                )
            },
            readOnly = true,
            enabled = false,
        )
        IconButton(onClick = {}, modifier = Modifier, enabled = false) {
            Icon(
                painter = painterResource(R.drawable.funnel),
                contentDescription = "filter",
            )
        }
    }
}
