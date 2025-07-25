package com.example.effectivemobiletask.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.effectivemobiletask.features.account.AccountScreen
import com.example.effectivemobiletask.features.auth.LoginScreenRoot
import com.example.effectivemobiletask.features.coursedetails.CourseDetailsScreen
import com.example.effectivemobiletask.features.favorites.FavoritesScreen
import com.example.effectivemobiletask.features.main.MainScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {


    val navBackStackEntry by
    navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavigation.MAIN.route
    val currenHierarchy = navBackStackEntry?.destination?.hierarchy

    /*Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigation.entries.forEachIndexed { index, navigationItem ->
                    val isSelected by remember(currentRoute){
                        derivedStateOf { currenHierarchy.any{it.hasRoute(navigationItem.route::class)} }
                    }

                    NavigationBarItem(
                        selected = isSelected,

                        label = { Text(navigationItem.name) },
                        icon = {
                            Icon(
                                painterResource(navigationItem.icon),
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
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

*//*                topLevelRoutes.forEach { topLevelRoute ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(topLevelRoute.icon),
                                contentDescription = null,
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected =
                            currentRoute?.hierarchy?.any {
                                it.hasRoute(topLevelRoute.route::class)
                            } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
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
                }*//*
            }
        }
*//*        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by
                    navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination

                topLevelRoutes.forEach { topLevelRoute ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(topLevelRoute.icon),
                                contentDescription = null,
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected =
                            currentRoute?.hierarchy?.any {
                                it.hasRoute(topLevelRoute.route::class)
                            } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
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
        }*//*
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Login,
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
        ) {
            composable<Login> {
                LoginScreenRoot(
                    onLoginClick = { navController.navigate(HomeGraph) }
                )
            }

            homeGraph(navController)
        }
    }*/
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeGraph>(startDestination = Main) {
        composable<Main> {
            MainScreen(
                onCourseClick = { courseId ->
                    navController.navigate(Course(courseId = courseId))
                }
            )
        }
        composable<Favorite> {
            FavoritesScreen(
                onCourseClick = { courseId ->
                    navController.navigate(Course(courseId = courseId))
                }
            )
        }
        composable<Account> { AccountScreen() }
        composable<Course> {
            CourseDetailsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
