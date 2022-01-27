package ru.wobcorp.justforpractice.presentation.filmslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.utils.FilmDiffUtilCallback

class FilmsAdapter : ListAdapter<FilmModel, FilmHolder>(FilmDiffUtilCallback()) {

    var onFilmItemClickListener: ((FilmModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(
            ListItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        val filmModel = getItem(position)
        holder.bind(filmModel)
        holder.itemView.setOnClickListener {
            onFilmItemClickListener?.invoke(filmModel)
        }
    }
}