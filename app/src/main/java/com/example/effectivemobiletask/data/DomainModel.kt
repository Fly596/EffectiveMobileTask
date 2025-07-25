package com.example.effectivemobiletask.data

import java.time.LocalDate

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: Double,
    val rate: Float,
    val startDate: LocalDate,
    val hasLike: Boolean,
    val publishDate: LocalDate,
)
