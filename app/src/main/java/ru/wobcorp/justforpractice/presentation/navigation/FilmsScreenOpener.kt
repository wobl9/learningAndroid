package ru.wobcorp.justforpractice.presentation.navigation

import com.github.terrakok.cicerone.Screen

interface FilmsScreenOpener {

    fun navigateToFilmsFragment(): Screen

    fun navigateToFilmDetailFragment(filmId: Int): Screen

}