package com.example.effectivemobiletask.navigation

import com.example.effectivemobiletask.R
import kotlinx.serialization.Serializable


sealed class Destinations {

    @Serializable
    data object Login: Destinations()

    @Serializable
    data object HomeGraph: Destinations()

    @Serializable
    data object Main : Destinations()

    @Serializable
    data class Favorite(val searchText: String? = null) : Destinations()

    @Serializable
    data object Account : Destinations()

    @Serializable
    data class CourseInfo(val courseId: Int): Destinations()
}

enum class BottomNavigation(val label: String, val icon: Int, val route: Destinations) {
    MAIN("Main", R.drawable.house, Destinations.Main),
    SEARCH("Favourite", R.drawable.search, Destinations.Favorite()),
    PROFILE("Profile", R.drawable.person, Destinations.Account);
}
/*
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
}*/
