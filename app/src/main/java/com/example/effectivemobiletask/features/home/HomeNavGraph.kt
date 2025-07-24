package com.example.effectivemobiletask.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.effectivemobiletask.features.account.AccountScreen
import com.example.effectivemobiletask.features.favorites.FavoritesScreen
import com.example.effectivemobiletask.features.main.MainScreen
import com.example.effectivemobiletask.navigation.HomeRoute

/*
@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController:
        NavHostController, // Он нам нужен, чтобы уйти на экран деталей
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute.Main,
        modifier = modifier,
    ) {
        composable<HomeRoute.Main> {
            MainScreen(
                onCourseClick = { courseId ->
                    rootNavController.navigate(
                        HomeRoute.Course(courseId = courseId)
                    )
                }
            )
        }
        composable<HomeRoute.Favorite> {
            FavoritesScreen(
                onCourseClick = { courseId ->
                    rootNavController.navigate(
                        HomeRoute.Course(courseId = courseId)
                    )
                }
            )
        }
        composable<HomeRoute.Account> { AccountScreen() }
    }
}
*/
