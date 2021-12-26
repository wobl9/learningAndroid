package ru.wobcorp.justforpractice.presentation.filmslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.presentation.filmslist.adapter.FilmHolder
import ru.wobcorp.justforpractice.utils.FilmDiffUtilCallback

class FilmsAdapter(
    private val listener: FilmHolder.OnFilmItemClickListener,
) : ListAdapter<FilmModel, FilmHolder>(FilmDiffUtilCallback()) {

    private var filmsList: List<FilmModel> = listOf()

    fun setItems(films: List<FilmModel>) {
        filmsList = films
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(
            ListItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(filmsList[position])
    }

    override fun getItemCount(): Int = filmsList.size

}