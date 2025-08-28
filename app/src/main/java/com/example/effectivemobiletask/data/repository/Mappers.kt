package com.example.effectivemobiletask.data.repository

import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseEntity
import com.example.effectivemobiletask.data.network.NetworkCourse
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Вспомогательная функция для конвертации строки с датой в Unix timestamp
// (Long).
fun convertStringToTimestamp(dateString: String, pattern: String): Long {
    // Создаем форматер на основе заданного шаблона (например, "yyyy-MM-dd").
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

    // Парсим строку в объект LocalDate.
    val localDate = LocalDate.parse(dateString, formatter)

    // Конвертируем LocalDate в миллисекунды эпохи.
    val epochMilli: Long =
        localDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

    return epochMilli
}

// Mapper (преобразователь) из сетевой модели (Network) в сущность БД (Entity).
// Вызывается после получения данных из сети перед сохранением в базу.
fun NetworkCourse.toEntity(): CourseEntity {
    val pattern = "yyyy-MM-dd"
    return CourseEntity(
        id = this.id,
        title = this.title,
        text = this.text,
        // Очищаем строку от пробелов и безопасно преобразуем в Double.
        price =
            this.price.filterNot { it.isWhitespace() }.toDoubleOrNull() ?: 0.0,
        rate = this.rate.toFloatOrNull() ?: 0.0f,
        // Преобразуем строку с датой в timestamp.
        startDate = convertStringToTimestamp(this.startDate, pattern),
        hasLike = this.hasLike,
        publishDate = convertStringToTimestamp(this.publishDate, pattern),
    )
}

// Mapper из сущности БД (Entity) в доменную модель (Domain).
// Вызывается, когда достали данные из БД для отображения на UI.
fun CourseEntity.toDomain(): Course {
    // Преобразуем timestamp (Long) обратно в объект Instant.
    val instantStart = Instant.ofEpochMilli(this.startDate)
    val instantPublish = Instant.ofEpochMilli(this.publishDate)
    // Конвертируем Instant в более удобный для UI LocalDate.
    val localStartDate =
        instantStart.atZone(ZoneId.systemDefault()).toLocalDate()
    val localPublishDate =
        instantPublish.atZone(ZoneId.systemDefault()).toLocalDate()
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

// Вспомогательные мапперы для списков.

// Преобразует список сущностей БД в список доменных моделей.
fun List<CourseEntity>.toDomain(): List<Course> {
    return this.map { it.toDomain() }
}

// Преобразует список сетевых моделей в список сущностей БД.
fun List<NetworkCourse>.toEntity(): List<CourseEntity> {
    return this.map { it.toEntity() }
}
