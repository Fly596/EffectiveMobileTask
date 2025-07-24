package com.example.effectivemobiletask

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthRoute{
    @Serializable data object Login : AuthRoute()
    @Serializable data object Registration : AuthRoute()
    @Serializable data object Onboarding : AuthRoute()
}

@Serializable
sealed class HomeRoute {
    @Serializable data object Main : HomeRoute()
    @Serializable data object Favorite : HomeRoute()
    @Serializable data object Account : HomeRoute()
    @Serializable data class Course(val courseId: Int) : HomeRoute()
}
