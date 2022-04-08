package ru.wobcorp.justforpractice.presentation.filmslist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.glide.RemoteImage
import ru.wobcorp.justforpractice.utils.getCornerRadius

class FilmHolder(
    private val binding: ListItemFilmBinding,
    private val listener: FilmsAdapter.OnFilmClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FilmModel) {
        Glide.with(itemView.context)
            .load(RemoteImage(item.imageLink))
            .transform(RoundedCorners(getCornerRadius(itemView.context)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.filmPoster)

        itemView.setOnClickListener {
            listener.onFilmClick(item)
        }
    }
}