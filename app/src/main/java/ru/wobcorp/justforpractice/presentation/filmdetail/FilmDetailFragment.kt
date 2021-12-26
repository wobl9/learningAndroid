package ru.wobcorp.justforpractice.presentation.filmdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmDetailFragmentBinding
import ru.wobcorp.justforpractice.utils.putArguments
import kotlin.properties.Delegates


class FilmDetailFragment : Fragment(R.layout.film_detail_fragment) {

    private var binding: FilmDetailFragmentBinding? = null
    private var filmId by Delegates.notNull<Int>()


    companion object {
        const val ARGS_FILM_ID = "film_id"
        fun newInstance(filmId: Int): FilmDetailFragment {
            return FilmDetailFragment().putArguments(ARGS_FILM_ID, filmId) as FilmDetailFragment
        }
    }

    private lateinit var viewModel: FilmDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmId = arguments?.getSerializable(ARGS_FILM_ID) as Int
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FilmDetailFragmentBinding.bind(view)
        binding?.filmOverview?.text = filmId.toString()

    }


}