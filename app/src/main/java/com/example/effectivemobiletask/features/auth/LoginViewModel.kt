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

// Определяем возможное событие.
sealed class UiEffect {
    data class OpenUrl(val url: String) : UiEffect()
}

data class LoginUiState(
    val email: String = "example@gmail.com",
    val password: String = "0000",
    val isLoginButtonEnabled: Boolean = false,
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _effectChanel = Channel<UiEffect>()
    val effect = _effectChanel.receiveAsFlow()

    fun onOpenOkClicked() {
        viewModelScope.launch {
            _effectChanel.send(UiEffect.OpenUrl("https://m.ok.ru/"))
        }
    }

    fun onOpenVkClicked() {
        viewModelScope.launch {
            _effectChanel.send(UiEffect.OpenUrl("https://vk.com/"))
        }
    }


    fun updateEmail(input: String) {
        _uiState.update {
            it.copy(
                email = input,
                isLoginButtonEnabled =
                    input.isNotBlank() && it.password.isNotBlank(),
            )
        }
    }

    fun updatePassword(input: String) {
        _uiState.update {
            it.copy(
                password = input,
                isLoginButtonEnabled =
                    input.isNotBlank() && it.email.isNotBlank(),
            )
        }
    }

    fun onLoginConfirm(email: String, password: String): Boolean {
        if (email.isNotBlank() && password.isNotBlank()) {
            return true
        } else {
            return false
        }
    }
}
