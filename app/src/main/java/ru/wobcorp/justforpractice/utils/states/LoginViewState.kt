package ru.wobcorp.justforpractice.utils.states

sealed class LoginViewState {
    class Success(val loginSuccess: Boolean) : LoginViewState()
    object Loading : LoginViewState()
    class Error(error: Throwable) : LoginViewState()
}