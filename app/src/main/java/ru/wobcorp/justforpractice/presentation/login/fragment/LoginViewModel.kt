package ru.wobcorp.justforpractice.presentation.login.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.emitSingleEvent
import ru.wobcorp.justforpractice.utils.singleEvent

class LoginViewModel : BaseViewModel() {

    val navigateMainScreen = singleEvent<Unit>()

    fun onAuthClick() {
        emitSingleEvent { navigateMainScreen.emit(Unit) }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }
    }
}