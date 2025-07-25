package com.example.effectivemobiletask.navigation

import kotlinx.serialization.Serializable

@Serializable data object Login

@Serializable data object HomeGraph

@Serializable data object Main

@Serializable data object Favorite

@Serializable data object Account

@Serializable data class Course(val courseId: Int)
