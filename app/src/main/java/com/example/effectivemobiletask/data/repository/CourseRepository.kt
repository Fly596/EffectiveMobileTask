package com.example.effectivemobiletask.data.repository

import android.util.Log
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseDao
import com.example.effectivemobiletask.data.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CourseRepository {
    fun fetchCourses(isAsc: Boolean? = null): Flow<List<Course>>

    suspend fun getCourseById(id: Int): Course?

    suspend fun refreshCourses()

    suspend fun setLike(id: Int)

    fun getFavoritesCourses(): Flow<List<Course>>
}

class CourseRepositoryImpl
@Inject
constructor(
    private val localDataSource: CourseDao,
    private val networkRepository: NetworkRepository,
) : CourseRepository {

    // Обновляет данные в БД из сети.
    override suspend fun refreshCourses() {
        withContext(Dispatchers.IO) {
            try {
                val networkCourses = networkRepository.loadCourses()
                val courseEntities =
                    networkCourses.courses.map { it.toEntity() }

                localDataSource.upsertAll(courseEntities)
            } catch (e: Exception) {
                Log.e("Courses repository", "failed to refresh", e)
            }
        }
    }

    // Получение данных из БД.
    override fun fetchCourses(isAsc: Boolean?): Flow<List<Course>> {
        return localDataSource.getAllCourses().map { entities ->
            val domainCOurses = entities.toDomain()

            when(isAsc){
                true -> domainCOurses.sortedBy {
                    it.publishDate
                }
                false -> domainCOurses.sortedByDescending { it.publishDate }
                null -> domainCOurses
            }
        }


    }

    override suspend fun getCourseById(id: Int): Course? {
        return withContext(Dispatchers.IO){
            localDataSource.getCourseById(id)?.toDomain()
        }
    }

    override suspend fun setLike(id: Int) {
        withContext(Dispatchers.IO){
            localDataSource.updateFavouriteStatus(id)

        }
    }

    override fun getFavoritesCourses(): Flow<List<Course>> {
        return localDataSource.getFavoritesCourses().map { it.toDomain() }
    }
}
