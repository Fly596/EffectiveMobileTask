package com.example.effectivemobiletask.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.effectivemobiletask.features.auth.LoginScreenRoot
import com.example.effectivemobiletask.features.coursedetails.CourseDetailsScreen
import com.example.effectivemobiletask.features.favorites.FavoritesScreen
import com.example.effectivemobiletask.features.main.MainScreen

// NavGraph, который управляет навигацией в приложении.
@Composable
@SuppressLint("RestrictedApi")
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Текущий маршрут.
    val currentRoute =
        navBackStackEntry?.destination?.route
            ?: BottomNavigation.MAIN.route::class.qualifiedName.orEmpty()

    // Текущий маршрут без параметра.
    val currentRouteTrimmed by
        remember(currentRoute) {
            derivedStateOf { currentRoute.substringAfter("?") }
        }

    // переменная, которая показывает, нужно ли показывать нижнюю навигацию.
    val shouldShowBottomBar =
        BottomNavigation.entries.any {
            it.route::class.qualifiedName == currentRouteTrimmed
        }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomAppBar {
                    BottomNavigation.entries.forEachIndexed {
                        index,
                        navigationItem ->
                        val isSelected by
                            remember(currentRoute) {
                                derivedStateOf {
                                    currentRouteTrimmed ==
                                        navigationItem.route::class
                                            .qualifiedName
                                }
                            }

                        NavigationBarItem(
                            selected = isSelected,
                            label = { Text(navigationItem.label) },
                            icon = {
                                Icon(
                                    painterResource(navigationItem.icon),
                                    contentDescription = navigationItem.label,
                                )
                            },
                            onClick = {
                                navController.navigate(navigationItem.route)
                            },
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Основной контейнер навигации, который управляет экранами.
        NavHost(
            navController = navController,
            startDestination = Destinations.Login, //!FOR TESTING, change to login.
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
        ) {
            // Экран экрана входа.
            composable<Destinations.Login> {
                LoginScreenRoot(
                    onLoginClick = {
                        navController.navigate(Destinations.HomeGraph)
                    }
                )
            }
            // Nested граф навигации для основных экранов приложения.
            navigation<Destinations.HomeGraph>(
                startDestination = Destinations.Main
            ) {
                composable<Destinations.Main> {
                    MainScreen(
                        onCourseClick = { id ->
                            navController.navigate(Destinations.CourseInfo(id))
                        }
                    )
                }

                composable<Destinations.Favorite> {
                    FavoritesScreen(
                        onCourseClick = {id->
                            navController.navigate(Destinations.CourseInfo(id))
                        }
                    )
                }

                composable<Destinations.Account> {
                    // Greeting("Profile")
                }
            }

            // Экран детальной информации о курсе.
            // TODO: доделать.
            composable<Destinations.CourseInfo> { backStackEntry ->
                val courseDetails: Destinations.CourseInfo = backStackEntry.toRoute()

                CourseDetailsScreen(onBackClick = {
                    navController.navigateUp()
                })
                // Greeting("Profile")
            }
        }
    }
}
/*

@Serializable
sealed class RootGraph(val route: String) {

    // Начальные страницы (вход, регистрация, онбординг).
    @Serializable data object Auth : RootGraph("auth_graph")

    // Основные страницы.
    @Serializable data object Home : RootGraph("home_graph")
}

fun NavGraphBuilder.authGraph(onLoginSuccess: () -> Unit) {

    navigation<RootGraph.Auth>(startDestination = AuthRoute.Login.route) {
        composable<AuthRoute.Login> {
            LoginScreenRoot(onLoginClick = onLoginSuccess)
        }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    //composable<RootGraph.Home> { HomeScreen(rootNavController = navController) }
    navigation<RootGraph.Home>(startDestination = HomeRoute.Main) {
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
*/
