package ru.wobcorp.justforpractice.presentation.filmslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmsFragmentBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.presentation.filmslist.adapter.FilmsAdapter
import ru.wobcorp.justforpractice.utils.SpaceItemDecoration
import ru.wobcorp.justforpractice.utils.observe
import ru.wobcorp.justforpractice.utils.requireActivityComponent
import ru.wobcorp.justforpractice.utils.states.FilmsViewState
import javax.inject.Inject

private const val DEFAULT_GRID_COUNT = 3
private const val DEFAULT_SPACING = 5

class FilmsFragment : Fragment(R.layout.films_fragment) {

    interface FilmDetailLauncher {
        fun launchFilmDetail(filmId: Int)
    }

    companion object {
        fun newInstance() = FilmsFragment()
    }

    @Inject
    lateinit var factory: FilmsViewModel.Factory
    private lateinit var adapter: FilmsAdapter
    private var _binding: FilmsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FilmsViewModel> { factory }

    override fun onAttach(context: Context) {
        requireActivityComponent<FilmsComponent>().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FilmsFragmentBinding.bind(view)
        adapter = FilmsAdapter(object : FilmsAdapter.OnFilmClickListener {
            override fun onFilmClick(filmModel: FilmModel) {
                openFilmDetail(filmModel.id)
            }
        })
        decorateRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openFilmDetail(filmId: Int) {
        (requireActivity() as FilmDetailLauncher).launchFilmDetail(filmId)
    }

    private fun observeViewModel() {
        viewModel.getFilms()
        viewModel.state.observe(lifecycleScope) {
            renderState(it)
        }
    }

    private fun renderState(state: FilmsViewState) {
        when (state) {
            is FilmsViewState.Loading -> {
                renderLoading()
            }
            is FilmsViewState.Success -> {
                adapter.submitList(state.data)
                renderSuccess()
            }
            is FilmsViewState.Error -> {
                renderError()
            }
        }
    }

    private fun renderLoading() {
        with(binding) {
            tvLoading.isVisible = true
            filmsList.isVisible = false
            tvError.isVisible = false
            btnRetry.isVisible = false
        }
    }

    private fun renderError() {
        with(binding) {
            tvLoading.isVisible = false
            filmsList.isVisible = true
            tvError.isVisible = true
            btnRetry.isVisible = true
            btnRetry.setOnClickListener {
                viewModel.getFilms()
            }
        }
    }

    private fun renderSuccess() {
        with(binding) {
            filmsList.adapter = adapter
            filmsList.isVisible = true
            tvLoading.isVisible = false
            tvError.isVisible = false
            btnRetry.isVisible = false
        }
    }

    private fun decorateRecyclerView() {
        binding.filmsList.addItemDecoration(
            SpaceItemDecoration(
                DEFAULT_GRID_COUNT,
                DEFAULT_SPACING
            )
        )
    }
}