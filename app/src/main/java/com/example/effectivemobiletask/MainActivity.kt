package com.example.effectivemobiletask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.effectivemobiletask.navigation.AppNavGraph
import com.example.effectivemobiletask.ui.theme.EffectiveMobileTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { EffectiveMobileTaskTheme { AppNavGraph() } }
    }
}
