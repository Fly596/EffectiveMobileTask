package com.example.effectivemobiletask.features.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth().padding(top = 140.dp),
        )

        // Поля ввода.
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.updateEmail(it) },
                keyboardOptions =
                    KeyboardOptions(
                        hintLocales = LocaleList(Locale("en")),
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.updatePassword(it) },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
            )
        }

        Button(
            onClick = {
                // Если ввод верный.
                onLoginClick.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Вход")
        }

        // Регистрация/ восстановление пароля.
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Нету аккаунта? Регистрация",
                modifier = Modifier.clickable(onClick = {}),
            )
            Text("Забыл пароль", modifier = Modifier.clickable(onClick = {}))
        }

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(onClick = {}, modifier = Modifier.weight(0.5f)) {
                Text("VK")
            }
            Button(onClick = {}, modifier = Modifier.weight(0.5f)) {
                Text("OK")
            }
        }
    }
}

@Composable
fun InputField(
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
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor =
                        MaterialTheme.colorScheme.surfaceBright,
                ),
        )
    }
}
