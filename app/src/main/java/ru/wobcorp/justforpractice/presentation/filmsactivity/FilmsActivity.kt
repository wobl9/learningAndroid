package ru.wobcorp.justforpractice.presentation.filmsactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmsActivityBinding
import ru.wobcorp.justforpractice.presentation.filmslist.DaggerFilmsComponent
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsComponent
import ru.wobcorp.justforpractice.presentation.navigation.FilmsScreenOpener
import ru.wobcorp.justforpractice.utils.ComponentProvider
import javax.inject.Inject

class FilmsActivity : AppCompatActivity(), ComponentProvider<FilmsComponent> {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FilmsActivity::class.java)
        }
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var filmsScreenOpener: FilmsScreenOpener

    @Inject
    lateinit var router: Router

    private var filmsComponent: FilmsComponent? = null

    private val navigator = AppNavigator(this, R.id.filmsContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FilmsActivityBinding.inflate(layoutInflater).root.let(::setContentView)

        filmsComponent = DaggerFilmsComponent.factory()
            .create(Application.dagger).also { it.inject(this) }

        if (savedInstanceState == null)
            router.replaceScreen(filmsScreenOpener.navigateToFilmsFragment())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun provideComponent(): FilmsComponent {
        return requireNotNull(filmsComponent) { "component not initialized" }
    }
}