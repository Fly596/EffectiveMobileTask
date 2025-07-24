package com.example.effectivemobiletask.data

import com.example.effectivemobiletask.data.source.local.CourseEntity
import com.example.effectivemobiletask.data.source.network.NetworkCourse
import java.time.Instant
import java.time.ZoneId

// Mapper из Network в Entity.
// Вызывается после получения данных из сети.
fun NetworkCourse.toEntity(): CourseEntity {
  val instantStart = Instant.parse(this.startDate)
  val instantPublish = Instant.parse(this.publishDate)
  return CourseEntity(
      id = this.id,
      title = this.title,
      text = this.text,
      price = this.price,
      rate = this.rate,
      startDate = instantStart.toEpochMilli(),
      hasLike = this.hasLike,
      publishDate = instantPublish.toEpochMilli(),
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