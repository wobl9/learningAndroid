package ru.wobcorp.justforpractice.presentation.navigation

import com.github.terrakok.cicerone.Screen

interface ScreenOpener {

    fun navigateToLoginFragment(): Screen

    fun navigateToFilmsActivity(): Screen

    fun navigateToFilmsFragment(): Screen

    fun navigateToFilmDetailFragment(filmId: Int): Screen

}