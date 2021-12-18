package ru.wobcorp.justforpractice.presentation.filmdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.wobcorp.justforpractice.databinding.FilmDetailFragmentBinding
import kotlin.properties.Delegates

private const val ARGS_FILM_ID = "film_id"

class FilmDetailFragment : Fragment() {

    private var _binding: FilmDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private var filmId by Delegates.notNull<Int>()


    companion object {
        fun newInstance(filmId: Int): FilmDetailFragment {
            val args = Bundle().apply {
                putSerializable(ARGS_FILM_ID, filmId)
            }
            return FilmDetailFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var viewModel: FilmDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmId = arguments?.getSerializable(ARGS_FILM_ID) as Int
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //по какой-то причине функция бинд не срабатывает, как в FilmsFragment
        //_binding = FilmDetailFragmentBinding.bind(view)
        binding.filmOverview.text = filmId.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}