package com.example.effectivemobiletask.data

import com.example.effectivemobiletask.data.source.local.CourseDao
import com.example.effectivemobiletask.data.source.network.NetworkDataSource
import javax.inject.Inject

interface CourseRepository {
    suspend fun fetchCourses(): List<Course>

    suspend fun getCourseById(id: Int): Course?
}

class CourseRepositoryImpl
@Inject
constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: CourseDao,
) : CourseRepository {

    override suspend fun fetchCourses(): List<Course> {

        // Проверка локальных данных.
        val courseEntities = localDataSource.getAllCourses()
        if (!courseEntities.isNullOrEmpty()) {
            return courseEntities.toDomain()
        }

        // Получаем данные из сети.
        val networkCourses = networkDataSource.loadCourses().toEntity()

        // Добавляем данные в БД.
        localDataSource.insertCourses(networkCourses)

        return networkCourses.toDomain()
    }

    override suspend fun getCourseById(id: Int): Course? {
        return localDataSource.getCourseById(id)?.toDomain()
    }
}
