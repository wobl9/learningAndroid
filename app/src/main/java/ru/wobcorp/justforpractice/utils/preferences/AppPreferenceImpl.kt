package ru.wobcorp.justforpractice.utils.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppPreference {

        companion object {
        private const val DEF_VALUE_STRING = ""
        private const val DEF_VALUE_INT = 0
    }

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, DEF_VALUE_STRING)
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, DEF_VALUE_INT)
    }
}