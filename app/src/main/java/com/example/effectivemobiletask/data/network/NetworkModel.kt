package com.example.effectivemobiletask.data.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCourse(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
)
