package ru.wobcorp.justforpractice.presentation.filmslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.FilmsFragmentBinding
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import ru.wobcorp.justforpractice.utils.observe
import javax.inject.Inject

class FilmsFragment : Fragment(R.layout.films_fragment) {

    private var _binding: FilmsFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: FilmsAdapter = FilmsAdapter(emptyList())

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmsFragmentBinding.inflate(inflater, container, false)
        binding.filmsList.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.filmsList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilms()
        viewModel.films.observe(lifecycleScope) {
            initializeView(it)
        }
    }

    private fun initializeView(list: List<FilmsSourceModel>) {
        for (page in list) {
            val filmList = page.films
            adapter = FilmsAdapter(filmList)
            binding.filmsList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}