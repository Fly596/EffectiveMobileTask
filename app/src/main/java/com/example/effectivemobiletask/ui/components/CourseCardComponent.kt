package com.example.effectivemobiletask.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.effectivemobiletask.R
import com.example.effectivemobiletask.data.Course

@Composable
fun CourseCard(
    course: Course,
    onAddToFavorite: (Int) -> Unit,
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
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        ) {
            CourseCardTopSection(
                rate = course.rate.toString(),
                publishDate = course.publishDate.toString(),
                onAddToFavorite = { onAddToFavorite(course.id) },
                isBookmarked = course.hasLike,
                // modifier = Modifier.weight(0.5f),
            )
            Box(){
                CourseCardBottomSection(
                    title = course.title,
                    text = course.text,
                    price = course.price.toString(),
                    // modifier = Modifier.weight(0.5f),
                )
                Row(
                    modifier =
                        Modifier.padding(horizontal = 16.dp)
                            .clickable(onClick = { { onCardClick(course.id) } }).align(Alignment.BottomEnd),
                ) {
                    Text(
                        "Подробнее",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Icon(
                        painter =
                            painterResource(R.drawable.outline_arrow_forward_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                    )
                }
            }

        }
    }
}

@Composable
fun CourseCardTopSection(
    rate: String = "6.6",
    publishDate: String = "Publish date",
    modifier: Modifier = Modifier,
    isBookmarked: Boolean = false,
    onAddToFavorite: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomStart,
    ) {
        Image(
            painter = painterResource(R.drawable.course_picture_default),
            contentDescription = "course picture",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier =
                Modifier.fillMaxWidth()
                    .height(132.dp)
                    .clip(RoundedCornerShape(12.dp)),
        )
        Row(
            modifier =
                Modifier.padding(start = 8.dp, bottom = 8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                modifier =
                    Modifier.background(
                            color =
                                Color(
                                    red = 0f,
                                    green = 0f,
                                    blue = 0f,
                                    alpha = 0.5f,
                                    colorSpace = ColorSpaces.Srgb,),
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
                                    red = 0f,
                                    green = 0f,
                                    blue = 0f,
                                    alpha = 0.5f,
                                    colorSpace = ColorSpaces.Srgb,),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = publishDate,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = onAddToFavorite, modifier = Modifier, colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(
                    red = 0f,
                    green = 0f,
                    blue = 0f,
                    alpha = 0.5f,
                    colorSpace = ColorSpaces.Srgb,)
            )) {
                Icon(
                    painter =
                        if (isBookmarked)
                            painterResource(R.drawable.bookmark_fill)
                        else painterResource(R.drawable.bookmark),
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
