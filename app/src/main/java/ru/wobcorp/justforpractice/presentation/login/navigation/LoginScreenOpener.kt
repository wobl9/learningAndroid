package ru.wobcorp.justforpractice.presentation.login.navigation

import com.github.terrakok.cicerone.Screen

interface LoginScreenOpener {

    fun navigateToLoginFragment(): Screen

    fun navigateToFilmsActivity(): Screen

}