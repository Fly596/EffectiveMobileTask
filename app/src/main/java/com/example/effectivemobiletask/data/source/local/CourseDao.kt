package com.example.effectivemobiletask.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

  @Query("SELECT * FROM courses") fun getAllCourses(): Flow<List<CourseEntity>>

  @Query("SELECT * FROM courses WHERE id = :id")
  suspend fun getCourseById(id: Int): CourseEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertAll(courses: List<CourseEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCourse(course: CourseEntity)

  @Query("DELETE FROM courses WHERE id = :id")
  suspend fun deleteCourseById(id: Int)

  @Query("DELETE FROM courses") suspend fun deleteAllCourses()

  @Query("UPDATE courses SET hasLike = :favStatus WHERE id = :id")
  suspend fun updateFavouriteStatus(id: Int, favStatus: Boolean)

  @Query(
      """ 
            SELECT * FROM courses ORDER BY
            CASE WHEN :isAsc = 1 THEN publishDate END ASC,
            CASE WHEN :isAsc = 0 THEN publishDate END DESC
      """)
  suspend fun getCoursesSortByPublishDate(isAsc: Boolean)
}
