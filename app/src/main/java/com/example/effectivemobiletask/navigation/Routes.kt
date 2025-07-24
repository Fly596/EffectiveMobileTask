package com.example.effectivemobiletask.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthRoute(val route: String){
    @Serializable data object Login : AuthRoute("login")
    @Serializable data object Registration : AuthRoute("registration")
    @Serializable data object Onboarding : AuthRoute("onboarding")
}

@Serializable
sealed class HomeRoute(val route: String) {
    @Serializable data object Main : HomeRoute("main")
    @Serializable data object Favorite : HomeRoute("favorite")
    @Serializable data object Account : HomeRoute("account")
    @Serializable data class Course(val courseId: Int) : HomeRoute("course")
}
