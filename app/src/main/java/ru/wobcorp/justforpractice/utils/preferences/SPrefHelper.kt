package ru.wobcorp.justforpractice.utils.preferences

interface SPrefHelper {

    fun saveString(key: String, value: String)

    fun saveInt(key: String, value: Int)

    fun getString(key: String): String

    fun getInt(key: String): Int
}