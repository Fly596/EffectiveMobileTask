package com.example.effectivemobiletask.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

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

class Converters {

  @TypeConverter
  fun fromDate(date: Date?): Long? {
    return date?.time
  }

  @TypeConverter
  fun toDate(value: Long?): Date? {
    return value?.let { Date(it) }
  }
}
