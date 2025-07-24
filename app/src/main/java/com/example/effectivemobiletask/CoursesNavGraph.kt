package com.example.effectivemobiletask

import kotlinx.serialization.Serializable
import okhttp3.Route

@Serializable
sealed class AuthScreens(val route: String){
    @Serializable data object Login: AuthScreens("login")
    @Serializable data object Registration: AuthScreens("registration")
    @Serializable data object Onboarding: AuthScreens("onboarding")
}

