package ru.wobcorp.justforpractice.utils.states

sealed class LoginViewState {
    class Success(val success: Boolean) : LoginViewState()
    object Loading : LoginViewState()
    class Error(error: Throwable) : LoginViewState()
}