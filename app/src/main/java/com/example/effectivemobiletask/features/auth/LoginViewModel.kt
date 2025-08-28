package com.example.effectivemobiletask.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Определяем возможные одноразовые события (эффекты), которые UI должен
// обработать.
sealed class UiEffect {
    // Событие для открытия ссылки в браузере.
    data class OpenUrl(val url: String) : UiEffect()
}

// Класс данных, который описывает состояние всего экрана.
data class LoginUiState(
    val email: String = "example@gmail.com",
    val password: String = "0000",
    val isLoginButtonEnabled: Boolean = false,
)

// ViewModel для экрана входа с использованием Hilt для внедрения зависимостей.
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    // Приватное, изменяемое состояние экрана.
    private val _uiState = MutableStateFlow(LoginUiState())
    // Публичное, неизменяемое состояние, на которое подписывается UI.
    val uiState = _uiState.asStateFlow()

    // Канал для отправки одноразовых событий в UI.
    private val _effectChanel = Channel<UiEffect>()
    val effect = _effectChanel.receiveAsFlow()

    // Обработчик клика по кнопке "ОК", отправляет эффект открытия URL.
    fun onOpenOkClicked() {
        viewModelScope.launch {
            _effectChanel.send(UiEffect.OpenUrl("https://m.ok.ru/"))
        }
    }

    // Обработчик клика по кнопке "VK", отправляет эффект открытия URL.
    fun onOpenVkClicked() {
        viewModelScope.launch {
            _effectChanel.send(UiEffect.OpenUrl("https://vk.com/"))
        }
    }

    // Функция для обновления значения email в состоянии.
    fun updateEmail(input: String) {
        _uiState.update {
            it.copy(
                email = input,
                // Кнопка входа активна, только если оба поля не пусты.
                isLoginButtonEnabled =
                    input.isNotBlank() && it.password.isNotBlank(),
            )
        }
    }

    // Функция для обновления значения пароля в состоянии.
    fun updatePassword(input: String) {
        _uiState.update {
            it.copy(
                password = input,
                // Кнопка входа активна, только если оба поля не пусты.
                isLoginButtonEnabled =
                    input.isNotBlank() && it.email.isNotBlank(),
            )
        }
    }

    // Простая проверка валидности данных для входа.
    fun onLoginConfirm(email: String, password: String): Boolean {
        if (email.isNotBlank() && password.isNotBlank()) {
            return true
        } else {
            return false
        }
    }
}
