package ru.wobcorp.justforpractice.presentation.filmslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmsFragmentBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import ru.wobcorp.justforpractice.presentation.filmslist.adapter.FilmsAdapter
import ru.wobcorp.justforpractice.utils.BaseViewState
import ru.wobcorp.justforpractice.utils.SpaceItemDecoration
import ru.wobcorp.justforpractice.utils.observe
import javax.inject.Inject

private const val DEFAULT_GRID_COUNT = 3
private const val DEFAULT_SPACING = 0

class FilmsFragment : Fragment(R.layout.films_fragment) {

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
        DaggerFilmsComponent.factory().create(Application.dagger).inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FilmsFragmentBinding.bind(view)
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
                is BaseViewState.Success<*> ->
                    initializeView(
                        (state.data as List<FilmModel>)
                    )
                is BaseViewState.Error -> initializeEmptyView()
            }
        }
    }

    private fun initializeEmptyView() {
        with(binding) {
            filmsList.visibility = View.INVISIBLE
            emptyList.visibility = View.VISIBLE
        }
    }

    private fun initializeView(list: List<FilmModel>) {
        adapter = FilmsAdapter()
        decorateRecyclerView()
        adapter.submitList(list)
        with(binding) {
            filmsList.adapter = adapter
            filmsList.visibility = View.VISIBLE
            emptyList.visibility = View.INVISIBLE
        }
        setupClickListener()
    }

    private fun decorateRecyclerView() {
        with(binding.filmsList) {
            layoutManager =
                StaggeredGridLayoutManager(DEFAULT_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
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
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, FilmDetailFragment.newInstance(it.id))
                .addToBackStack(null)
                .commit()
        }
    }
}