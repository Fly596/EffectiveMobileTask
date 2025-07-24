package com.example.effectivemobiletask.data.repository

import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseDao
import com.example.effectivemobiletask.data.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import javax.inject.Inject

interface CourseRepository {
    fun fetchCourses(): Flow<List<Course>>

    suspend fun getCourseById(id: Int): Course?

    suspend fun refreshCourses()
}

class CourseRepositoryImpl
@Inject
constructor(
    private val okHttpClient: OkHttpClient,
    private val localDataSource: CourseDao,
) : CourseRepository {
    private val fileUrl = "https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download"

    // Обновляет данные в БД из сети.
    override suspend fun refreshCourses() {
        withContext(Dispatchers.IO){
            val request = Request.Builder().url(fileUrl).build()

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

            }
        }
    }

    // Получение данных из БД.
    override fun fetchCourses(): Flow<List<Course>> {
        return localDataSource.getAllCourses().map { it.toDomain() }
    }

    override suspend fun getCourseById(id: Int): Course? {
        return localDataSource.getCourseById(id)?.toDomain()
    }
}
