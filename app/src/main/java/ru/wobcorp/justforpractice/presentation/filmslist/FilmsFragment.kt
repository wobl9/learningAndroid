package ru.wobcorp.justforpractice.presentation.filmslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmsFragmentBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.presentation.filmslist.adapter.FilmsAdapter
import ru.wobcorp.justforpractice.utils.BaseViewState
import ru.wobcorp.justforpractice.utils.SpaceItemDecoration
import ru.wobcorp.justforpractice.utils.observe
import javax.inject.Inject

private const val DEFAULT_GRID_COUNT = 3
private const val DEFAULT_SPACING = 5

class FilmsFragment : Fragment(R.layout.films_fragment) {

    interface OnFilmClickListener{
        fun onFilmClick(filmId: Int)
    }

    companion object {
        fun newInstance() = FilmsFragment()
    }

    @Inject
    lateinit var factory: FilmsViewModel.Factory

    private lateinit var onFilmClickListener: OnFilmClickListener
    private lateinit var adapter: FilmsAdapter
    private var _binding: FilmsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FilmsViewModel> { factory }

    override fun onAttach(context: Context) {
        DaggerFilmsComponent.factory().create(Application.dagger).inject(this)
        super.onAttach(context)
        if (context is OnFilmClickListener){
            onFilmClickListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FilmsFragmentBinding.bind(view)
        adapter = FilmsAdapter()
        decorateRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.getFilms()
        viewModel.state.observe(lifecycleScope) {
            renderingState(it)
        }
    }

    private fun renderingState(state: BaseViewState){
        @Suppress("UNCHECKED_CAST")
        when(state){
            is BaseViewState.Loading -> {
            }
            is BaseViewState.Success<*> -> {
                adapter.submitList(state.data as List<FilmModel>)
                bindingSuccessRender()
                setupClickListener()
            }
            is BaseViewState.Error -> {
                bindingErrorRender()
            }
        }
    }

    private fun bindingErrorRender() {
        with(binding) {
            tvLoading.isVisible = false
            tvError.isVisible = true
            btnRetry.isVisible = true
            btnRetry.setOnClickListener {
                viewModel.getFilms()
            }
        }
    }

    private fun bindingSuccessRender() {
        with(binding) {
            filmsList.adapter = adapter
            filmsList.isVisible = true
            tvLoading.isVisible = false
        }
    }

    private fun decorateRecyclerView() {
        with(binding.filmsList) {
            addItemDecoration(
                SpaceItemDecoration(
                    DEFAULT_GRID_COUNT,
                    DEFAULT_SPACING
                )
            )
        }
    }

    private fun setupClickListener() {
        adapter.onFilmItemClickListener = {
            onFilmClickListener.onFilmClick(it.id)
        }
    }
}