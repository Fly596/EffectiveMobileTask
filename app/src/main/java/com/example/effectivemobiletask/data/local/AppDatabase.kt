package com.example.effectivemobiletask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

// Определяет основной класс базы данных для Room.
@Database(
    // Список сущностей (таблиц), которые будут в базе данных.
    entities = [
        CourseEntity::class
    ],
    // Версия базы данных. Необходимо увеличивать при изменении схемы.
    version = 1,
    // Отключает экспорт схемы в JSON файл.
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Абстрактный метод, который возвращает DAO для работы с таблицей курсов.
    abstract fun courseDao(): CourseDao
}
