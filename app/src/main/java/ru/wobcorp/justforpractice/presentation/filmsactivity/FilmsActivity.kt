package ru.wobcorp.justforpractice.presentation.filmsactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.MainActivityBinding
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import ru.wobcorp.justforpractice.presentation.filmslist.DaggerFilmsComponent
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsComponent
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsFragment
import ru.wobcorp.justforpractice.utils.ComponentProvider
import ru.wobcorp.justforpractice.utils.replaceWithBackStack
import ru.wobcorp.justforpractice.utils.replaceWithoutBackStack

class FilmsActivity : AppCompatActivity(), FilmsFragment.FilmDetailLauncher,
    ComponentProvider<FilmsComponent> {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FilmsActivity::class.java)
        }
    }

    private var filmsComponent: FilmsComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityBinding.inflate(layoutInflater).root.let(::setContentView)

        filmsComponent = DaggerFilmsComponent.factory()
            .create(Application.dagger).also { it.inject(this) }

        if (savedInstanceState == null)
            supportFragmentManager.replaceWithoutBackStack(
                R.id.mainContainer,
                FilmsFragment.newInstance()
            )
    }

    override fun provideComponent(): FilmsComponent {
        return requireNotNull(filmsComponent) { "component not initialized" }
    }

    override fun launchFilmDetail(filmId: Int) {
        supportFragmentManager.replaceWithBackStack(
            R.id.mainContainer,
            FilmDetailFragment.newInstance(filmId)
        )
    }
}