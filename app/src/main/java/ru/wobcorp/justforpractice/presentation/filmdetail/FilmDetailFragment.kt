package ru.wobcorp.justforpractice.presentation.filmdetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

class FilmDetailFragment : Fragment(R.layout.film_detail_fragment) {

    companion object {
        private const val ARGS_FILM_ID = "film_id"
        fun newInstance(filmId: Int): Fragment {
            return FilmDetailFragment().putArguments(ARGS_FILM_ID, filmId)
        }
    }

    @Inject
    internal lateinit var factory: FilmDetailViewModel.AssistedFactory

    private var filmId by Delegates.notNull<Int>()
    private var _binding: FilmDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmDetailViewModel by viewModels {
        FilmDetailViewModel.provideFactory(factory, filmId)
    }

    override fun onAttach(context: Context) {
        DaggerFilmsComponent.factory().create(Application.dagger).inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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

    private fun parseArgs() {
        filmId = arguments?.getInt(ARGS_FILM_ID) ?: 0
        if (filmId == 0) {
            Timber.d(getString(R.string.args_not_received))
        }
    }

    private fun observeViewModel() {
        viewModel.getFilmById()
        viewModel.state.observe(lifecycleScope) { state ->
            renderState(state)
        }
    }

    private fun renderState(state: BaseViewState) {
        @Suppress("UNCHECKED_CAST")
        when (state) {
            is BaseViewState.Loading -> {
                binding.tvLoading.isVisible = true
            }
            is BaseViewState.Success<*> -> bindSuccessRendering(
                (state.data as FilmModel)
            )
            is BaseViewState.Error -> bindErrorRendering()
        }
    }

    private fun bindSuccessRendering(filmModel: FilmModel) {
        with(binding) {
            tvLoading.isVisible = false
            filmPoster.isVisible = true
            filmTitle.isVisible = true
            filmOverview.isVisible = true
            filmTitle.text = filmModel.title
            filmOverview.text = filmModel.overview
        }
        binding.filmPoster.let {
            Glide.with(this)
                .load(RemoteImage(filmModel.imageLink))
                .fitCenter()
                .into(it)
        }
    }

    private fun bindErrorRendering() {
        with(binding) {
            tvLoading.isVisible = false
            tvError.isVisible = true
            btnRetry.isVisible = true
            btnRetry.setOnClickListener {
                viewModel.getFilmById()
            }
        }
    }
}