package ru.wobcorp.justforpractice.presentation.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import ru.wobcorp.justforpractice.presentation.filmsactivity.FilmsActivity
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsFragment
import ru.wobcorp.justforpractice.presentation.login.fragment.LoginFragment
import javax.inject.Inject

class ScreenOpenerImpl @Inject constructor() : ScreenOpener {

    override fun navigateToLoginFragment(): Screen = FragmentScreen {
        LoginFragment.newInstance()
    }

    override fun navigateToFilmsActivity(): Screen = ActivityScreen {
        FilmsActivity.getIntent(it)
    }

    override fun navigateToFilmsFragment(): Screen = FragmentScreen {
        FilmsFragment.newInstance()
    }

    override fun navigateToFilmDetailFragment(filmId: Int): Screen = FragmentScreen {
        FilmDetailFragment.newInstance(filmId)
    }
}