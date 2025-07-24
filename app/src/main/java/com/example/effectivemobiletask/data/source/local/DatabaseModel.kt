package com.example.effectivemobiletask.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: Long,
    val hasLike: Boolean,
    val publishDate: Long,
)

