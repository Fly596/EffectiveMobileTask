package com.example.effectivemobiletask.data.network

import kotlinx.serialization.Serializable

// Класс для десериализации корневого объекта JSON ответа.
@Serializable
data class NetworkResponse(val courses: List<NetworkCourse>)

// Класс (DTO) для десериализации объекта курса из JSON.
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
