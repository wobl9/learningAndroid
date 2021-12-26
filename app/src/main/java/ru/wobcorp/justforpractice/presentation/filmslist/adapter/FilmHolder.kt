package ru.wobcorp.justforpractice.presentation.filmslist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.glide.RemoteImage

class FilmHolder (
    private val binding: ListItemFilmBinding,
    private val listener: OnFilmItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    interface OnFilmItemClickListener {
        fun onFilmItemClick(filmId: Int)
    }

    fun bind(item: FilmModel) {
        itemView.setOnClickListener {
            listener.onFilmItemClick(item.id)
        }

        Glide.with(itemView.context)
            .load(RemoteImage(item.imageLink))
            .into(binding.filmPoster)
    }
}