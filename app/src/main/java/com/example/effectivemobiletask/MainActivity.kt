package com.example.effectivemobiletask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.effectivemobiletask.ui.BottomBarItem
import com.example.effectivemobiletask.ui.theme.EffectiveMobileTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { EffectiveMobileTaskTheme { CoursesNavGraph() } }
    }
}

@Composable
fun HomeScreen(
    // Для главного графа.
    rootNavController: NavHostController,
) {

    // Для вложенного графа с главными экранами.
    val homeNavController = rememberNavController()

    // Список вкладок.
    val bottomBarItems = listOf(
        BottomBarItem.Main,
        BottomBarItem.Favorite,
        BottomBarItem.Account,
    )

    // Отслеживание текущего пути во вложенном графе.
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Определяем, нужно ли показывать нижнюю панель.
    val shouldShowBottomBar =
        bottomBarItems.any { it.route.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    bottomBarItems.forEach { barItem ->
                        val isSelected =
                            currentDestination?.hierarchy?.any { it.route == barItem.route.route } == true

                        NavigationBarItem(
                            label = { Text(barItem.title) },
                            icon = {
                                Icon(
                                    painter = painterResource(barItem.iconDefault),
                                    contentDescription = barItem.title,
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                homeNavController.navigate(barItem.route) {
                                    popUpTo(homeNavController.graph.findStartDestination().id) {
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
        },
    ) { innerPadding ->
        HomeNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = homeNavController,
            rootNavController = rootNavController,
        )

    }
}
