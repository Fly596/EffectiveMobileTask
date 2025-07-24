package com.example.effectivemobiletask.data.repository

import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseEntity
import com.example.effectivemobiletask.data.network.NetworkCourse
import java.time.Instant
import java.time.ZoneId

// Mapper из Network в Entity.
// Вызывается после получения данных из сети.
fun NetworkCourse.toEntity(): CourseEntity {
  return CourseEntity(
      id = this.id,
      title = this.title,
      text = this.text,
      price = this.price.toDoubleOrNull()?:0.0,
      rate = this.rate.toFloatOrNull()?:0.0f,
      startDate = Instant.parse(this.startDate).toEpochMilli(),
      hasLike = this.hasLike,
      publishDate = Instant.parse(this.publishDate).toEpochMilli(),
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
        price = this.price.toBigDecimal(),
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
