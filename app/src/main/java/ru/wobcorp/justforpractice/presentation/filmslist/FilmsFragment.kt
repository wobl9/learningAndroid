package ru.wobcorp.justforpractice.presentation.filmslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.cabriole.decorator.ColumnProvider
import io.cabriole.decorator.GridBoundsMarginDecoration
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmsFragmentBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import ru.wobcorp.justforpractice.utils.observe
import javax.inject.Inject

private const val DEFAULT_GRID_COUNT = 3

class FilmsFragment : Fragment(R.layout.films_fragment) {

    interface Callbacks {
        fun onFilmSelected(filmId: Int)
    }

    private var _binding: FilmsFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: FilmsAdapter = FilmsAdapter()

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
        _binding = FilmsFragmentBinding.bind(view)
        viewModel.getFilms()
        viewModel.films.observe(lifecycleScope) {
            initializeView(it)
        }
    }

    private fun initializeView(list: List<FilmsSourceModel>) {
        binding.filmsList.layoutManager =
            StaggeredGridLayoutManager(DEFAULT_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
        decorateRecyclerView()
        if (list.isNotEmpty()) {
            val filmList: List<FilmModel> = list.first().films
            adapter.setItems(filmList)
            binding.filmsList.adapter = adapter
            binding.emptyList.visibility = View.INVISIBLE
            binding.filmsList.visibility = View.VISIBLE
        }
    }

    private fun decorateRecyclerView() {
        binding.filmsList.addItemDecoration(GridBoundsMarginDecoration.create(
            context?.resources?.getDimensionPixelSize(R.dimen.left_right_margin)!!,
            columnProvider = object : ColumnProvider {
                override fun getNumberOfColumns(): Int = DEFAULT_GRID_COUNT
            }
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}