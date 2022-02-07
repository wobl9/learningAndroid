package ru.wobcorp.justforpractice.presentation.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.MainActivityBinding
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsFragment
import ru.wobcorp.justforpractice.utils.replace

class MainActivity : AppCompatActivity(), FilmsFragment.OnFilmClickListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityBinding.inflate(layoutInflater).root.let(::setContentView)
        supportActionBar?.hide()

        if (savedInstanceState == null)
            supportFragmentManager.replace(
                R.id.mainContainer,
                FilmsFragment.newInstance()
            )
    }

    override fun onFilmClick(filmId: Int) {
        supportFragmentManager.replace(R.id.mainContainer,
            FilmDetailFragment.newInstance(filmId))
    }
}