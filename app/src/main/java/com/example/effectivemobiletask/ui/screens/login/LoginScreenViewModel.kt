package com.example.effectivemobiletask.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Определяем возможное событие.
sealed class UiEvent{
    data class OpenUrl(val url: String):UiEvent()
}

@HiltViewModel
class LoginScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onOpenOkClicked(){
        sendEvent(UiEvent.OpenUrl("https://m.ok.ru/"))
    }

    fun onOpenVkClicked(){
        sendEvent(UiEvent.OpenUrl("https://vk.com/"))
    }

    private fun sendEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
