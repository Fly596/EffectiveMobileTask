package com.example.effectivemobiletask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Сущность (Entity), представляющая таблицу "courses" в базе данных.
@Entity(tableName = "courses")
data class CourseEntity(
    // Первичный ключ таблицы.
    @PrimaryKey
    val id: Int,
    val title: String,
    val text: String,
    val price: Double,
    val rate: Float,
    val startDate: Long,
    val hasLike: Boolean,
    val publishDate: Long,
)
