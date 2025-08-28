package com.example.effectivemobiletask.features.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.effectivemobiletask.R
import com.example.effectivemobiletask.ui.components.EmailTextField

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
) {
    val context = LocalContext.current
    val state by
        viewModel.uiState.collectAsStateWithLifecycle() // Подписка на стейт.

    LaunchedEffect(key1 = true) { // Сайд-эффекты (одноразовый запуск).
        viewModel.effect.collect { effect ->
            when (effect) {
                is UiEffect.OpenUrl -> { // Открытие ссылки из эффекта VM.
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
                    context.startActivity(intent)
                }
            }
        }
    }

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

        // Блок с email/паролем.
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            LoginInputFields(
                state.email,
                state.password,
                viewModel::updateEmail,
                viewModel::updatePassword,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        // Кнопка логина.
        Button(
            onClick = { onLoginClick.invoke() },
            modifier = Modifier.fillMaxWidth(),
            enabled =
                state.isLoginButtonEnabled, // Активна только если поля валидные
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
        ) {
            Text("Вход", modifier = Modifier)
        }

        // Ссылки на регистрацию/восстановление.
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Нету аккаунта? Регистрация",
                modifier = Modifier.clickable(onClick = {}),
            )
            Text(
                "Забыл пароль",
                modifier = Modifier.clickable(onClick = {}),
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight(500),
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color(0xFF4D555E),
        )

        // Соцсети (VK, OK).
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(
                onClick = { viewModel.onOpenVkClicked() },
                modifier = Modifier.weight(0.5f),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2683ED),
                        contentColor = Color.White,
                    ),
            ) {
                Image(
                    painterResource(R.drawable.icon_vk),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(36.dp),
                )

                // Text("VK")
            }
            Button(
                onClick = { viewModel.onOpenOkClicked() },
                modifier = Modifier.weight(0.5f),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF98509),
                        contentColor = Color.White,
                    ),
            ) {
                Image(
                    painterResource(R.drawable.icon_ok),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(36.dp),
                )

                // Text("OK")
            }
        }
    }
}

@Composable
fun LoginInputFields(
    emailValue: String,
    passwordValue: String,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Поле ввода email.
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Email", style = MaterialTheme.typography.titleMedium)
            EmailTextField(
                emailValue = emailValue,
                onEmailUpdate = onEmailValueChange,
            )
        }
        // Поле ввода пароля.
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Пароль", style = MaterialTheme.typography.titleMedium)
            InputField(
                value = passwordValue,
                onValueChange = onPasswordValueChange,
                label = "Password",
                modifier,
            )
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPassword: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation =
            if (isPassword)
                PasswordVisualTransformation() // Скрываем символы для пароля.
            else VisualTransformation.None,
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        colors =
            OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor =
                    MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor =
                    MaterialTheme.colorScheme.surfaceContainer,
            ),
    )
}
