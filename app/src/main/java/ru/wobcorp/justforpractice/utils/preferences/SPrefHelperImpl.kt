package ru.wobcorp.justforpractice.utils.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class SPrefHelperImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SPrefHelper {

    companion object {
        private const val DEF_VALUE_STRING = ""
        private const val DEF_VALUE_INT = -1
    }

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, DEF_VALUE_STRING).toString()
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, DEF_VALUE_INT)
    }
}