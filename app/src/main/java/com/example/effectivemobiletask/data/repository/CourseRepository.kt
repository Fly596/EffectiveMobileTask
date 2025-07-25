package com.example.effectivemobiletask.data.repository

import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseDao
import com.example.effectivemobiletask.data.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

interface CourseRepository {
    fun fetchCourses(isAsc: Boolean? = null): Flow<List<Course>>

    suspend fun getCourseById(id: Int): Course?

    suspend fun refreshCourses()
}

class CourseRepositoryImpl
@Inject
constructor(
    private val okHttpClient: OkHttpClient,
    private val localDataSource: CourseDao,
    private val networkRepository: NetworkRepository,
) : CourseRepository {

    // Обновляет данные в БД из сети.
    override suspend fun refreshCourses() {
        withContext(Dispatchers.IO){
            val networkCourses= networkRepository.loadCourses()
            if(networkCourses != null){
                localDataSource.upsertAll(networkCourses.courses.toEntity())
            }
/*            val request = Request.Builder().url(fileUrl).build()

            try{
                val response = okHttpClient.newCall(request).execute()
                if(response.isSuccessful){
                    val jsonString = response.body?.string()
                    if(!jsonString.isNullOrEmpty()){
                        val json = Json{
                            ignoreUnknownKeys = true
                        }
                        val networkCourses = json.decodeFromString<NetworkResponse>(jsonString)
                        localDataSource.upsertAll(networkCourses.courses.toEntity())
                    }else{
                        // error handle
                    }
                }
            }catch (e: IOException){
                // error handle

            }*/
        }
    }

    // Получение данных из БД.
    override fun fetchCourses(isAsc: Boolean?): Flow<List<Course>> {
        return if(isAsc == null){
            localDataSource.getAllCourses().map { it.toDomain() }
        }else{
            localDataSource.getCoursesSortByPublishDate(isAsc = isAsc).map { it.toDomain() }
        }
    }

    override suspend fun getCourseById(id: Int): Course? {
        return localDataSource.getCourseById(id)?.toDomain()
    }


}
