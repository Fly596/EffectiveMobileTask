package com.example.effectivemobiletask.data.network

import javax.inject.Inject

interface NetworkRepository {
  suspend fun loadCourses(): List<NetworkCourse>
}

class NetworkRepositoryImpl
@Inject
constructor(private val apiService: ApiService) : NetworkRepository {

  override suspend fun loadCourses(): List<NetworkCourse> =
      apiService.getCourses()
}
