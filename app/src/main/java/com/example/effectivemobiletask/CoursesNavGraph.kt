package com.example.effectivemobiletask

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.effectivemobiletask.ui.screens.coursedetails.CourseDetailScreen
import com.example.effectivemobiletask.ui.screens.login.LoginScreen
import kotlinx.serialization.Serializable


@Composable
fun CoursesNavGraph(
    modifier: Modifier = Modifier,
    startDestination: RootGraph = RootGraph.Auth,
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authGraph(
            onLoginSuccess = {
                navController.navigate(RootGraph.Home) {
                    popUpTo(RootGraph.Auth) { inclusive = true }
                }
            },
        )
        homeGraph(navController)
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
            LoginScreen(
                onLoginClick = onLoginSuccess,
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    composable<RootGraph.Home> {
        HomeScreen(rootNavController = navController)
    }
    composable<HomeRoute.Course> {
        CourseDetailScreen(onBackClick = { navController.popBackStack() })
    }

}
