package ru.wobcorp.justforpractice.presentation.filmslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import javax.inject.Inject

class FilmsFragment : Fragment(R.layout.films_fragment) {

    companion object {
        fun newInstance() = FilmsFragment()
    }

    @Inject
    lateinit var factory: FilmsViewModel.Factory

    private val viewModel by viewModels<FilmsViewModel> { factory }

    override fun onAttach(context: Context) {
        DaggerFilmsComponent.factory().create(Application.dagger).inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilms()
    }
}