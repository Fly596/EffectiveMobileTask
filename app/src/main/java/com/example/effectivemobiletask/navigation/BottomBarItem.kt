package com.example.effectivemobiletask.navigation

import com.example.effectivemobiletask.R

enum class BottomNavigation(val label: String, val icon: Int, val route: Any){
    MAIN("Main",R.drawable.house,Main),
    FAVORITE("Favorite",R.drawable.bookmark,Favorite),
    ACCOUNT("Account",R.drawable.person,Account),
}

/*data class TopLevelRoute<T : Any>(
    val route: T,
    val name: String,
    val icon: Int,
)

val topLevelRoutes =
    listOf(
        TopLevelRoute(Main, "Main", R.drawable.house),
        TopLevelRoute(Favorite, "Favorite", R.drawable.bookmark),
        TopLevelRoute(Account, "Account", R.drawable.person),
    )*/

/*sealed class BottomBarItem<T : Any>(
    val route: T,
    val title: String,
    val iconDefault: Int,
    val iconSelected: Int,
) {
    data object Main :
        BottomBarItem(
            Main,
            "Main",
            R.drawable.house,
            R.drawable.house_selected,
        )

    data object Favorite :
        BottomBarItem(
            Favorite,
            "Favorites",
            R.drawable.bookmark,
            R.drawable.bookmark_selected,
        )

    data object Account :
        BottomBarItem(
            Account,
            "Account",
            R.drawable.person,
            R.drawable.person_selected,
        )
}*/
