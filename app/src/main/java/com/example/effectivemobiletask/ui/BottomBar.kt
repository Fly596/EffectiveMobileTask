package com.example.effectivemobiletask.ui

import com.example.effectivemobiletask.HomeRoute
import com.example.effectivemobiletask.R

sealed class BottomBarItem(
    val route: HomeRoute,
    val title: String,
    val iconDefault: Int,
    val iconSelected: Int,
) {
    data object Main :
        BottomBarItem(
            HomeRoute.Main, "Main",
            R.drawable.house,
            R.drawable.house_selected)

    data object Favorite :
        BottomBarItem(
            HomeRoute.Favorite,
            "Favorites",
            R.drawable.bookmark,
            R.drawable.bookmark_selected)

    data object Account :
        BottomBarItem(
            HomeRoute.Account,
            "Account",
            R.drawable.person,
            R.drawable.person_selected)
}
