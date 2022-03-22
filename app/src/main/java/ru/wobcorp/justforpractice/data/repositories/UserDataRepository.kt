package ru.wobcorp.justforpractice.data.repositories

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.utils.preferences.SPrefHelperImpl
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val sPrefHelper: SPrefHelperImpl
) {

    companion object {
        private const val SP_KEY_LOGIN = "key_login"
        private const val SP_KEY_PASSWORD = "key_password"
        private const val SP_LOGIN_VALUE = "qwerty"
        private const val SP_PASSWORD_VALUE = 1111
    }

    init {
        sPrefHelper.saveString(SP_KEY_LOGIN, SP_LOGIN_VALUE)
        sPrefHelper.saveInt(SP_KEY_PASSWORD, SP_PASSWORD_VALUE)
    }

    fun checkAuthSuccess(login: String, password: Int): Single<Boolean> {
        return Single.fromCallable {
            val rightLogin = sPrefHelper.getString(SP_KEY_LOGIN)
            val rightPassword = sPrefHelper.getInt(SP_KEY_PASSWORD)
            login == rightLogin && password == rightPassword
        }
    }
}