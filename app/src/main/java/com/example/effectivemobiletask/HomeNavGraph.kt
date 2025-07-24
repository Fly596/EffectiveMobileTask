package com.example.effectivemobiletask

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController: NavHostController, // Он нам нужен, чтобы уйти на экран деталей

) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute.Main,
        modifier = modifier,
    ) {
        composable<HomeRoute.Main> {
            MainScreen(
                onCourseClick = { courseId ->
                    rootNavController.navigate(HomeRoute.Course(courseId = courseId))
                },
            )
        }
        composable<HomeRoute.Favorite> {
            FavoriteScreen(
                onCourseClick = { courseId ->
                    rootNavController.navigate(HomeRoute.Course(courseId = courseId))
                },
            )
        }
        composable<HomeRoute.Account> {
            AccountScreen()
        }

    }
}

@Composable fun AccountScreen() {
    TODO("Not yet implemented")
}

@Composable fun FavoriteScreen(onCourseClick: (Int) -> Unit) {
    TODO("Not yet implemented")
}

@Composable fun MainScreen(onCourseClick: (Int) -> Unit) {
    TODO("Not yet implemented")
}
