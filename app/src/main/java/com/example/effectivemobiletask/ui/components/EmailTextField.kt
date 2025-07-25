package com.example.effectivemobiletask.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList

@Composable
fun EmailTextField(emailValue: String, onEmailUpdate:(String) ->Unit){
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    val isError = emailValue.isNotEmpty() && !emailValue.matches(emailPattern)

    OutlinedTextField(
        value = emailValue,
        onValueChange = {onEmailUpdate(it) },
        label = { Text("Email") },
        // Передаем флаг ошибки в TextField, чтобы он подсветился красным
        isError = isError,
        supportingText = {
            // Показываем текст ошибки, если она есть
            if (isError) {
                Text(
                    text = "Неверный формат email",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Email,
                hintLocales = LocaleList(Locale("en")),
                imeAction = ImeAction.Next,
            ),
        singleLine = true
    )
}
