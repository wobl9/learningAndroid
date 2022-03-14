package ru.wobcorp.justforpractice.data.repositories

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.utils.preferences.AppPreferenceImpl
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val appPreferenceImpl: AppPreferenceImpl
) {

    companion object {
        private const val SP_KEY_LOGIN = "key_login"
        private const val SP_KEY_PASSWORD = "key_password"
        private const val SP_LOGIN_VALUE = "qwerty"
        private const val SP_PASSWORD_VALUE = 1111
        private const val DEF_VALUE_LOGIN = ""
        private const val DEF_VALUE_PASSWORD = 0
    }

    init {
        appPreferenceImpl.putString(SP_KEY_LOGIN, SP_LOGIN_VALUE)
        appPreferenceImpl.putInt(SP_KEY_PASSWORD, SP_PASSWORD_VALUE)
    }

    fun checkAuthSuccess(inputLogin: String?, inputPassword: String?): Single<Boolean> {
        return Single.fromCallable {
            val login = parseLogin(inputLogin)
            val password = parsePassword(inputPassword)
            val rightLogin = appPreferenceImpl.getString(SP_KEY_LOGIN)
            val rightPassword = appPreferenceImpl.getInt(SP_KEY_PASSWORD)
            login == rightLogin && password == rightPassword
        }
    }

    private fun parseLogin(inputLogin: String?): String {
        return inputLogin?.trim() ?: DEF_VALUE_LOGIN
    }

    private fun parsePassword(inputPassword: String?): Int {
        return try {
            inputPassword?.trim()?.toInt() ?: DEF_VALUE_PASSWORD
        } catch (e: Exception) {
            DEF_VALUE_PASSWORD
        }
    }
}