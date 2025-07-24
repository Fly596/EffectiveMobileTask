package com.example.effectivemobiletask.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Определяем возможное событие.
sealed class UiEvent {
    data class OpenUrl(val url: String) : UiEvent()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginButtonEnabled: Boolean = false,
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onOpenOkClicked() {
        sendEvent(UiEvent.OpenUrl("https://m.ok.ru/"))
    }

    fun onOpenVkClicked() {
        sendEvent(UiEvent.OpenUrl("https://vk.com/"))
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
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
