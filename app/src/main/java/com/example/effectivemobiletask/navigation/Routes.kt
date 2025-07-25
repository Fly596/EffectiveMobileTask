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

// Компоненты навигации.
enum class BottomNavigation(val label: String, val icon: Int, val route: Destinations) {
    MAIN("Main", R.drawable.house, Destinations.Main),
    SEARCH("Favourite", R.drawable.search, Destinations.Favorite()),
    PROFILE("Profile", R.drawable.person, Destinations.Account);
}
