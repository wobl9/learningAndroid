package ru.wobcorp.justforpractice.presentation.login.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginActivityBinding
import ru.wobcorp.justforpractice.presentation.login.fragment.LoginFragment
import ru.wobcorp.justforpractice.presentation.navigation.ScreenOpener
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginFragment.FilmsLauncher {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var screenOpener: ScreenOpener

    @Inject
    lateinit var router: Router

    private val navigator = AppNavigator(this, R.id.loginContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityBinding.inflate(layoutInflater).root.let(::setContentView)
        Application.dagger.inject(this)

        if (savedInstanceState == null) {
            router.replaceScreen(screenOpener.navigateToLoginFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun launchFilms() {
        router.replaceScreen(screenOpener.navigateToFilmsActivity())
    }
}