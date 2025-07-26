package com.example.effectivemobiletask.data.network

import android.util.Log
import javax.inject.Inject
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

interface NetworkRepository {
    suspend fun loadCourses(): NetworkResponse?
}

class NetworkRepositoryImpl
@Inject
constructor(private val okHttpClient: OkHttpClient) : NetworkRepository {
    private val fileUrl =
        "https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download"

    override suspend fun loadCourses(): NetworkResponse? {
        val request = Request.Builder().url(fileUrl).build()

        return try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val jsonString = response.body?.string()
                if (!jsonString.isNullOrEmpty()) {
                    val json = Json { ignoreUnknownKeys = true }
                    val networkCourses =
                        json.decodeFromString<NetworkResponse>(jsonString)
                    return networkCourses
                    // localDataSource.upsertAll(networkCourses.courses.toEntity())
                } else {
                    Log.e("loadcourses", "empty string")
                    null
                }
            } else {
                null
            }
        } catch (e: IOException) {

            Log.e("loadcourses", "Error fetching api data", e)
            null
        }
    }
}
