package ru.wobcorp.justforpractice.utils.preferences

interface AppPreference {

    fun setLogin(login: String)

    fun setPassword(password: Int)

    fun getLogin(): String

    fun getPassword(): Int
}