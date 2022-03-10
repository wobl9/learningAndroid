package ru.wobcorp.justforpractice.utils.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppPreference {

    companion object {
        private const val SP_KEY_LOGIN = "key_login"
        private const val SP_KEY_PASSWORD = "key_password"
        private const val DEF_VALUE_STRING = ""
        private const val DEF_VALUE_PASSWORD = 0
    }

    private val editor = sharedPreferences.edit()

    override fun setLogin(login: String) {
        saveLogin(login)
    }

    override fun setPassword(password: Int) {
        savePassword(password)
    }

    override fun getLogin(): String {
        return getString(SP_KEY_LOGIN)
    }

    override fun getPassword(): Int {
        return getInt(SP_KEY_PASSWORD)
    }

    private fun saveLogin(login: String) {
        editor.putString(SP_KEY_LOGIN, login).apply()
    }

    private fun savePassword(password: Int) {
        editor.putInt(SP_KEY_PASSWORD, password).apply()
    }

    private fun getString(key: String, defaultValue: String = DEF_VALUE_STRING): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    private fun getInt(key: String, defaultValue: Int = DEF_VALUE_PASSWORD): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }
}