package ru.wobcorp.justforpractice.presentation.filmslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.utils.FilmDiffUtilCallback

class FilmsAdapter(
    private val onFilmClickListener: OnFilmClickListener
) : ListAdapter<FilmModel, FilmHolder>(FilmDiffUtilCallback()) {

    interface OnFilmClickListener {
        fun onFilmClick(filmModel: FilmModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(
            ListItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onFilmClickListener
        )
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        val filmModel = getItem(position)
        holder.bind(filmModel)
    }
}