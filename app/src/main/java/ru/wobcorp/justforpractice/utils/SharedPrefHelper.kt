package ru.wobcorp.justforpractice.utils

import android.content.Context
import android.content.SharedPreferences
import ru.wobcorp.justforpractice.di.ApplicationModule

class SharedPrefHelper(context: Context) {

    private var sharedPreferences: SharedPreferences =
        ApplicationModule().providePreferences(context)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

}