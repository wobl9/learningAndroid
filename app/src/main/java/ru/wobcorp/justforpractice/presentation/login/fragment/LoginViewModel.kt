package ru.wobcorp.justforpractice.presentation.login.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.emitSingleEvent
import ru.wobcorp.justforpractice.utils.singleEvent

class LoginViewModel : BaseViewModel() {

    companion object {
        private const val DEFAULT_STRING = ""
        private const val DEFAULT_NUMBER = 0
        private const val COUNT_OF_SYMBOLS = 6
    }

    val navigateMainScreen = singleEvent<Unit>()
    val errorInputLogin = singleEvent<Boolean>()
    val errorInputPassword = singleEvent<Boolean>()

    fun onAuthClick() {
        emitSingleEvent { navigateMainScreen.emit(Unit) }
    }

    fun checkUserData(inputLogin: String?, inputPassword: String?): Boolean {
        val login = parseLogin(inputLogin)
        val password = parsePassword(inputPassword)
        return validateInputFields(login, password)
    }

    private fun parseLogin(inputLogin: String?): String {
        return inputLogin?.trim() ?: DEFAULT_STRING
    }

    private fun parsePassword(inputPassword: String?): Int {
        return try {
            inputPassword?.trim()?.toInt() ?: DEFAULT_NUMBER
        } catch (e: Exception) {
            DEFAULT_NUMBER
        }
    }

    private fun validateInputFields(login: String, password: Int): Boolean {
        var result = true
        if (login.isBlank() || login.length < COUNT_OF_SYMBOLS) {
            emitSingleEvent {
                errorInputLogin.emit(true)
            }
            result = false
        }
        if (password.toString().length < COUNT_OF_SYMBOLS) {
            emitSingleEvent {
                errorInputPassword.emit(true)
            }
            result = false
        }
        return result
    }

    fun resetErrorInputLogin() {
        emitSingleEvent {
            errorInputLogin.emit(false)
        }
    }

    fun resetErrorInputPassword() {
        emitSingleEvent {
            errorInputPassword.emit(false)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }
    }
}