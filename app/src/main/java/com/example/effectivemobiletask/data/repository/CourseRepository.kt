package com.example.effectivemobiletask.data.repository

import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseDao
import com.example.effectivemobiletask.data.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CourseRepository {
    fun fetchCourses(): Flow<List<Course>>

    suspend fun getCourseById(id: Int): Course?

    suspend fun refreshCourses()
}

class CourseRepositoryImpl
@Inject
constructor(
    private val networkRepository: NetworkRepository,
    private val localDataSource: CourseDao,
) : CourseRepository {

    // Обновляет данные в БД из сети.
    override suspend fun refreshCourses() {
        // Получаем данные из сети.
        val networkCourses = networkRepository.loadCourses()
        localDataSource.deleteAllCourses()

        // Добавляем данные в БД.
        localDataSource.upsertAll(networkCourses.toEntity())
    }

    // Получение данных из БД.
    override fun fetchCourses(): Flow<List<Course>> {
        return localDataSource.getAllCourses().map { it.toDomain() }
    }

    override suspend fun getCourseById(id: Int): Course? {
        return localDataSource.getCourseById(id)?.toDomain()
    }
}
