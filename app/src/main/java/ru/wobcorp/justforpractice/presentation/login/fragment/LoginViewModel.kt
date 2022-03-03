package ru.wobcorp.justforpractice.presentation.login.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wobcorp.justforpractice.domain.usecases.AppAuthUseCase
import ru.wobcorp.justforpractice.utils.*
import ru.wobcorp.justforpractice.utils.states.LoginViewState
import javax.inject.Inject

class LoginViewModel(
    private val appAuthUseCase: AppAuthUseCase
) : BaseViewModel() {

    val navigateMainScreen = singleEvent<Unit>()

    val loginState = event<LoginViewState>(LoginViewState.Loading)

    fun onAuthClick() {
        emitSingleEvent { navigateMainScreen.emit(Unit) }
    }

    fun checkUserData(inputLogin: String?, inputPassword: String?) {
        appAuthUseCase.execute(inputLogin, inputPassword).get(
            disposable = disposables,
            onError = { error ->
                loginState.value = LoginViewState.Error(error)
            },
            onSuccess = { boolean ->
                loginState.value = LoginViewState.Success(boolean)
            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val appAuthUseCase: AppAuthUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(appAuthUseCase = appAuthUseCase) as T
        }
    }
}