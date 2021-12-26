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
import ru.wobcorp.justforpractice.presentation.filmslist.adapter.FilmHolder
import ru.wobcorp.justforpractice.presentation.filmslist.adapter.FilmsAdapter
import ru.wobcorp.justforpractice.utils.BaseViewState
import ru.wobcorp.justforpractice.utils.SpaceItemDecoration
import ru.wobcorp.justforpractice.utils.observe
import ru.wobcorp.justforpractice.utils.replace
import javax.inject.Inject

private const val DEFAULT_GRID_COUNT = 3
private const val DEFAULT_SPACING = 10

class FilmsFragment : Fragment(R.layout.films_fragment),
    FilmHolder.OnFilmItemClickListener {

    companion object {
        fun newInstance() = FilmsFragment()
    }

    @Inject
    lateinit var factory: FilmsViewModel.Factory

    private var _binding: FilmsFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: FilmsAdapter = FilmsAdapter(this)
    private val viewModel by viewModels<FilmsViewModel> { factory }

    override fun onAttach(context: Context) {
        DaggerFilmsComponent.factory().create(Application.dagger).inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FilmsFragmentBinding.bind(view)
        decorateRecyclerView()
        viewModel.getFilms()
        viewModel.state.observe(lifecycleScope) { state ->
            when (state) {
                is BaseViewState.Loading -> initializeEmptyView()
                is BaseViewState.Success<*> -> viewModel.films.observe(lifecycleScope) {
                    initializeView(it)
                }
                is BaseViewState.Error -> initializeEmptyView()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFilmItemClick(filmId: Int) {
        parentFragmentManager.replace(
            R.id.mainContainer, FilmDetailFragment.newInstance(filmId)
        )
    }

    private fun initializeView(list: List<FilmModel>) {
        adapter.setItems(list)
        binding.filmsList.adapter = adapter
        binding.emptyList.visibility = View.INVISIBLE
        binding.filmsList.visibility = View.VISIBLE
    }

    private fun initializeEmptyView() {
        binding.emptyList.visibility = View.VISIBLE
        binding.filmsList.visibility = View.INVISIBLE
    }

    private fun decorateRecyclerView() {
        binding.filmsList.layoutManager =
            StaggeredGridLayoutManager(DEFAULT_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
        binding.filmsList.addItemDecoration(
            SpaceItemDecoration(
                DEFAULT_GRID_COUNT,
                DEFAULT_SPACING
            )
        )
    }
}