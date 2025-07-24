package com.example.effectivemobiletask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
    rootNavController: NavHostController
) {

  // Для вложенного графа с главными экранами.
  val homeNavController = rememberNavController()


    // Список вкладок.
    val bottomBarItems = listOf(
        BottomBarItem.Main,
        BottomBarItem.Favorite,
        BottomBarItem.Account,
    )
}
