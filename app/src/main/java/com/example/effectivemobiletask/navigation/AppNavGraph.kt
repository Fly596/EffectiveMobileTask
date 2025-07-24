package com.example.effectivemobiletask.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.effectivemobiletask.features.account.AccountScreen
import com.example.effectivemobiletask.features.auth.LoginScreen
import com.example.effectivemobiletask.features.coursedetails.CourseDetailsScreen
import com.example.effectivemobiletask.features.favorites.FavoritesScreen
import com.example.effectivemobiletask.features.main.MainScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startDestination: RootGraph = RootGraph.Auth,
) {
    val navController: NavHostController = rememberNavController()

    // Список вкладок.
    val bottomBarItems =
        listOf(
            BottomBarItem.Main,
            BottomBarItem.Favorite,
            BottomBarItem.Account,
        )

    Scaffold(
        bottomBar = {
            // Получение текущего маршрута.
            val navBackStackEntry by
                navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            val shouldShowBottomBar =
                when (currentDestination) {
                    "main",
                    "favorite",
                    "account" -> true
                    else -> false
                }

            if (shouldShowBottomBar) {
                NavigationBar {
                    bottomBarItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(item.iconDefault),
                                    contentDescription = item.title,
                                )
                            },
                            selected = currentDestination == item.route.route,
                            label = { Text(item.title) },
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(
                                        navController.graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding),
        ) {
            authGraph(
                onLoginSuccess = {
                    navController.navigate(RootGraph.Home) {
                        popUpTo(RootGraph.Auth) { inclusive = true }
                    }
                }
            )
            homeGraph(navController)
        }
    }
}

@Serializable
sealed class RootGraph(val route: String) {

    // Начальные страницы (вход, регистрация, онбординг).
    @Serializable data object Auth : RootGraph("auth_graph")

    // Основные страницы.
    @Serializable data object Home : RootGraph("home_graph")
}

fun NavGraphBuilder.authGraph(onLoginSuccess: () -> Unit) {

    navigation<RootGraph.Auth>(startDestination = AuthRoute.Login) {
        composable<AuthRoute.Login> {
            LoginScreen(onLoginClick = onLoginSuccess)
        }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    //composable<RootGraph.Home> { HomeScreen(rootNavController = navController) }
    navigation<RootGraph.Home>(startDestination = HomeRoute.Main){
        composable<HomeRoute.Main> {
            MainScreen(
                onCourseClick = { courseId ->
                    navController.navigate(HomeRoute.Course(courseId = courseId))
                },
            )
        }
        composable<HomeRoute.Favorite> {
            FavoritesScreen(
                onCourseClick = { courseId ->
                    navController.navigate(HomeRoute.Course(courseId = courseId))
                },
            )
        }
        composable<HomeRoute.Account> {
            AccountScreen()
        }
        composable<HomeRoute.Course> {
            CourseDetailsScreen(onBackClick = { navController.popBackStack() })
        }
    }

}
