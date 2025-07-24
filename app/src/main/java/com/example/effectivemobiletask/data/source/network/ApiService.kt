package com.example.effectivemobiletask.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
  @GET("courses/{id}") suspend fun getCourse(@Path("id") id: Int): NetworkCourse

  @GET("courses") suspend fun getCourses(): List<NetworkCourse>
}
