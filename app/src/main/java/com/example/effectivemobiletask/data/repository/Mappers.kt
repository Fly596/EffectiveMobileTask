package com.example.effectivemobiletask.data.repository

import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseEntity
import com.example.effectivemobiletask.data.network.NetworkCourse
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertStringToTimestamp(dateString: String, pattern: String): Long {

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

    val localDate = LocalDate.parse(dateString, formatter)

    val epochMilli: Long = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    return epochMilli
}

// Mapper из Network в Entity.
// Вызывается после получения данных из сети.
fun NetworkCourse.toEntity(): CourseEntity {
    val pattern = "yyyy-MM-dd"
  return CourseEntity(
      id = this.id,
      title = this.title,
      text = this.text,
      price = this.price.filterNot { it.isWhitespace() }.toDoubleOrNull()?:0.0,
      rate = this.rate.toFloatOrNull()?:0.0f,
      startDate = convertStringToTimestamp(this.startDate,pattern),
      hasLike = this.hasLike,
      publishDate = convertStringToTimestamp(this.publishDate, pattern)/* Instant.parse(this.publishDate).toEpochMilli()*/,
  )
}

// Mapper из Entity в Domain.
// Вызывается, когда достали данные из БД для отображения на UI.
fun CourseEntity.toDomain(): Course {
    val instantStart = Instant.ofEpochMilli(this.startDate)
    val instantPublish = Instant.ofEpochMilli(this.publishDate)
    val localStartDate = instantStart.atZone(ZoneId.systemDefault()).toLocalDate()
    val localPublishDate = instantPublish.atZone(ZoneId.systemDefault()).toLocalDate()
    return Course(
        id = this.id,
        title = this.title,
        text = this.text,
        price = this.price,
        rate = this.rate,
        startDate = localStartDate,
        hasLike = this.hasLike,
        publishDate = localPublishDate,
    )
}

// Мапперы для списка.
fun List<CourseEntity>.toDomain():List<Course>{
    return this.map { it.toDomain() }
}


fun List<NetworkCourse>.toEntity():List<CourseEntity>{
    return this.map { it.toEntity() }
}
