package com.example.effectivemobiletask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// DAO (Data Access Object) - интерфейс для взаимодействия с таблицей курсов.
@Dao
interface CourseDao {

    // Запрос на получение всех курсов в виде Flow, что позволяет отслеживать
    // изменения в реальном времени.
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    // Запрос на получение одного курса по его ID. suspend указывает на то, что
    // это корутина.
    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Int): CourseEntity?

    // Вставляет или обновляет список курсов. OnConflictStrategy.REPLACE
    // заменяет старую запись при конфликте.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(courses: List<CourseEntity>)

    // Вставляет или обновляет один курс.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    // Удаляет курс по его ID.
    @Query("DELETE FROM courses WHERE id = :id")
    suspend fun deleteCourseById(id: Int)

    // Удаляет все записи из таблицы курсов.
    @Query("DELETE FROM courses") suspend fun deleteAllCourses()

    // Обновляет статус "избранное" для курса, инвертируя текущее значение.
    @Query("UPDATE courses SET hasLike = NOT hasLike WHERE id = :id")
    suspend fun updateFavouriteStatus(id: Int)

    // Запрос для получения курсов, отсортированных по дате публикации (по
    // возрастанию или убыванию).
    @Query(
        """ 
            SELECT * FROM courses ORDER BY
            CASE WHEN :isAsc = 1 THEN publishDate END ASC,
            CASE WHEN :isAsc = 0 THEN publishDate END DESC
      """
    )
    fun getCoursesSortByPublishDate(isAsc: Boolean): Flow<List<CourseEntity>>

    // Запрос для получения только тех курсов, которые добавлены в избранное.
    @Query("SELECT * FROM courses WHERE hasLike")
    fun getFavoritesCourses(): Flow<List<CourseEntity>>
}
