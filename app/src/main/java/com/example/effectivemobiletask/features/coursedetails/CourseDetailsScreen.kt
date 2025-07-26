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
import androidx.compose.material3.Button
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
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R
import com.example.effectivemobiletask.ui.theme.EffectiveMobileTaskTheme
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
    publishDate: String? = null,
    image: Int = R.drawable.course_picture_default,
) {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Image(
            painter = painterResource(R.drawable.course_picture_default),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()
        ){
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()){
                IconButton(
                    onClick = {},
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        painterResource(R.drawable.outline_arrow_back_ios_new_24),
                        contentDescription = null,
                    )
                }

                IconButton(
                    onClick = {},
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        painterResource(R.drawable.bookmark),
                        contentDescription = null,
                    )
                }
            }

        }

        Row(modifier = Modifier.align(Alignment.BottomStart), horizontalArrangement = Arrangement.spacedBy(8.dp)){
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
                // Rate.
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
                    text = publishDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }

        }
        /*
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)){

            Box(modifier = Modifier.fillMaxWidth()){
                Row(
                    modifier = Modifier.align(Alignment.BottomStart), horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                        // Rate.
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
                            text = publishDate.toString(),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                }
            }

        }*/




    }
}

@Preview
@Composable
fun MyPreview() {
    EffectiveMobileTaskTheme {
        Box(modifier = Modifier.fillMaxSize()) { TopSection() }
    }
}
