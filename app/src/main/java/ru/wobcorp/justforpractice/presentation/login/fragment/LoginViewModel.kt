package ru.wobcorp.justforpractice.presentation.login.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import ru.wobcorp.justforpractice.domain.usecases.AppAuthUseCase
import ru.wobcorp.justforpractice.presentation.login.navigation.LoginScreenOpener
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.event
import ru.wobcorp.justforpractice.utils.get
import ru.wobcorp.justforpractice.utils.states.LoginViewState
import javax.inject.Inject

class LoginViewModel(
    private val appAuthUseCase: AppAuthUseCase,
    private val loginScreenOpener: LoginScreenOpener,
    private val router: Router
) : BaseViewModel() {

    val loginState = event<LoginViewState>(LoginViewState.Loading)

    fun onAuthClick() {
        router.newRootScreen(loginScreenOpener.navigateToFilmsActivity())
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
        private val appAuthUseCase: AppAuthUseCase,
        private val loginScreenOpener: LoginScreenOpener,
        private val router: Router
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(
                appAuthUseCase = appAuthUseCase,
                loginScreenOpener = loginScreenOpener,
                router = router
            ) as T
        }
    }
}