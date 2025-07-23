package com.example.effectivemobiletask.data.source.local

import androidx.room.Entity
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "courses")
data class LocalCourse(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: Date,
    val hasLike: Boolean,
    val publishDate: Date,
)

class Converters {

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time?.toLong()
  }

  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }
}
