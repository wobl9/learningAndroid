package ru.wobcorp.justforpractice.utils.preferences

interface AppPreference {

    fun putString(key: String, value: String)

    fun putInt(key: String, value: Int)

    fun getString(key: String): String?

    fun getInt(key: String): Int
}