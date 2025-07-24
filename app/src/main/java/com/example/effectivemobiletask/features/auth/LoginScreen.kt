package com.example.effectivemobiletask.features.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        state.value,
        onLoginClick = {
            viewModel.onLoginConfirm()
        },
    )


}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 140.dp),
        )

        // Поля ввода.
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            InputField(
                title = "Email",
                label = "example@gmail.com",
                value = state.email,
            )
            InputField(
                title = "Пароль",
                label = "Введите пароль",
                value = state.password,
            )
        }

        Button(
            onClick = {onLoginClick},
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Вход")
        }

        // Регистрация/ восстановление пароля.
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Нету аккаунта? Регистрация", modifier = Modifier.clickable(onClick = {}))
            Text("Забыл пароль", modifier = Modifier.clickable(onClick = {}))
        }

        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Button(
                onClick = {
                },
                modifier = Modifier.weight(0.5f),
            ) {
                Text("VK")
            }
            Button(
                onClick = {
                },
                modifier = Modifier.weight(0.5f),
            ) {
                Text("OK")
            }
        }
    }
}

@Composable fun InputField(
    modifier: Modifier = Modifier,
    title: String,
    label: String,
    value: String,
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = input,
            label = { Text(label) },
            onValueChange = { input = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            ),
        )

    }
}

