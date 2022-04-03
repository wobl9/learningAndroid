package ru.wobcorp.justforpractice.presentation.login.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.wobcorp.justforpractice.presentation.filmsactivity.FilmsActivity
import ru.wobcorp.justforpractice.presentation.login.fragment.LoginFragment
import javax.inject.Inject

class LoginScreenOpenerImpl @Inject constructor() : LoginScreenOpener {

    override fun navigateToLoginFragment(): Screen = FragmentScreen {
        LoginFragment.newInstance()
    }

    override fun navigateToFilmsActivity(): Screen = ActivityScreen {
        FilmsActivity.getIntent(it)
    }
}