package com.example.effectivemobiletask.data.network

import android.util.Log
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import javax.inject.Inject

// Интерфейс, определяющий контракт для сетевого репозитория.
interface NetworkRepository {
    suspend fun loadCourses(): NetworkResponse
}

// Реализация сетевого репозитория.
class NetworkRepositoryImpl
@Inject
constructor(private val okHttpClient: OkHttpClient) : NetworkRepository {
    // URL для загрузки данных.
    private val fileUrl =
        "https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download"

    // Функция для загрузки и парсинга данных о курсах.
    override suspend fun loadCourses(): NetworkResponse {
        // Создание запроса с помощью OkHttp.
        val request = Request.Builder().url(fileUrl).build()

        try {
            // Выполнение синхронного запроса.
            val response = okHttpClient.newCall(request).execute()

            // Проверка успешности ответа.
            if (!response.isSuccessful) {
                throw IOException("Запрос не удался")
            }

            // Получение тела ответа в виде строки.
            val jsonString = response.body?.string()
            if (jsonString.isNullOrEmpty()) {
                throw IOException("Тело ответа пустое")
            }

            // Настройка JSON парсера (kotlinx.serialization).
            val json = Json { ignoreUnknownKeys = true }
            // Десериализация JSON строки в объект NetworkResponse.
            val networkCourses =
                json.decodeFromString<NetworkResponse>(jsonString)
            return networkCourses
        } catch (e: IOException) {
            // Логирование и проброс ошибки.
            Log.e("loadcourses", "Ошибка при загрузке данных", e)
            throw e
        }
    }
}
