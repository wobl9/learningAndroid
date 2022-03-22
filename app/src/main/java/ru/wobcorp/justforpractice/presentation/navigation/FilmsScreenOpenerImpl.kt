package ru.wobcorp.justforpractice.presentation.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsFragment
import javax.inject.Inject

class FilmsScreenOpenerImpl @Inject constructor() : FilmsScreenOpener {

    override fun navigateToFilmsFragment(): Screen = FragmentScreen {
        FilmsFragment.newInstance()
    }

    override fun navigateToFilmDetailFragment(filmId: Int): Screen = FragmentScreen {
        FilmDetailFragment.newInstance(filmId)
    }
}