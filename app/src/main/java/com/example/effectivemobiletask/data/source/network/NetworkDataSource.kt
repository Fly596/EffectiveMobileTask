package com.example.effectivemobiletask.data.source.network

import javax.inject.Inject

interface NetworkDataSource {
  suspend fun loadCourses(): List<NetworkCourse>
}

class NetworkDataSourceImpl
@Inject
constructor(private val apiService: ApiService) : NetworkDataSource {

  override suspend fun loadCourses(): List<NetworkCourse> =
      apiService.getCourses()
}
