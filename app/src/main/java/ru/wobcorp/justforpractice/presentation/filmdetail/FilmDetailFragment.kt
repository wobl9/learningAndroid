package ru.wobcorp.justforpractice.presentation.filmdetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmDetailFragmentBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.glide.RemoteImage
import ru.wobcorp.justforpractice.presentation.filmslist.DaggerFilmsComponent
import ru.wobcorp.justforpractice.utils.BaseViewState
import ru.wobcorp.justforpractice.utils.observe
import ru.wobcorp.justforpractice.utils.putArguments
import javax.inject.Inject
import kotlin.properties.Delegates

class FilmDetailFragment : Fragment(R.layout.film_detail_fragment) {

    companion object {
        private const val ARGS_FILM_ID = "film_id"
        fun newInstance(filmId: Int): FilmDetailFragment {
            return FilmDetailFragment().putArguments(ARGS_FILM_ID, filmId) as FilmDetailFragment
        }
    }

    @Inject
    internal lateinit var factory: FilmDetailViewModel.AssistedFactory

    private var filmId by Delegates.notNull<Int>()
    private var _binding: FilmDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmDetailViewModel by viewModels {
        filmId = arguments?.getInt(ARGS_FILM_ID) as Int
        FilmDetailViewModel.provideFactory(factory, filmId)
    }

    override fun onAttach(context: Context) {
        DaggerFilmsComponent.factory().create(Application.dagger).inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FilmDetailFragmentBinding.bind(view)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.getFilms()
        viewModel.state.observe(lifecycleScope) { state ->
            @Suppress("UNCHECKED_CAST")
            when (state) {
                is BaseViewState.Loading -> initializeEmptyView()
                is BaseViewState.Success<*> -> initializeView(
                    (state.data as FilmModel)
                )
                is BaseViewState.Error -> initializeEmptyView()
            }
        }
    }

    private fun initializeEmptyView() {
        with(binding) {
            filmPoster.visibility = View.INVISIBLE
            filmOverview.visibility = View.INVISIBLE
            filmOverview.text = getString(R.string.film_detail_not_found)
        }
    }

    private fun initializeView(filmModel: FilmModel) {
        with(binding) {
            filmPoster.visibility = View.VISIBLE
            filmTitle.text = filmModel.title
            filmOverview.visibility = View.VISIBLE
            filmOverview.text = filmModel.overview
        }
        binding.filmPoster.let {
            Glide.with(this)
                .load(RemoteImage(filmModel.imageLink))
                .fitCenter()
                .into(it)
        }
    }
}