package com.example.effectivemobiletask.features.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), onLoginClick: () -> Unit) {
  val context = LocalContext.current
}
