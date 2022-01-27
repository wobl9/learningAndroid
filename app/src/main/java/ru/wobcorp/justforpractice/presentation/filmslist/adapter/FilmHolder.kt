package ru.wobcorp.justforpractice.presentation.filmslist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.glide.RemoteImage

class FilmHolder(
    private val binding: ListItemFilmBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FilmModel) {
        Glide.with(itemView.context)
            .load(RemoteImage(item.imageLink))
            .into(binding.filmPoster)
    }
}