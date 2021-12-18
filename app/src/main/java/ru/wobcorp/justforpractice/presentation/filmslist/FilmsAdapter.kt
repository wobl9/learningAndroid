package ru.wobcorp.justforpractice.presentation.filmslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.wobcorp.justforpractice.databinding.ListItemFilmBinding
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.glide.RemoteImage
import ru.wobcorp.justforpractice.utils.FilmDiffUtilCallback

class FilmsAdapter : ListAdapter<FilmModel, FilmsAdapter.FilmHolder>(FilmDiffUtilCallback()) {

    private var filmsList: List<FilmModel> = mutableListOf()

    fun setItems(films: List<FilmModel>) {
        filmsList = films.map { film ->
            FilmModel(film.id, film.title, film.overview, film.imageLink)
        }
    }

    class FilmHolder(private val binding: ListItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilmModel) {
            itemView.setOnClickListener {
                val callback = it.context as FilmsFragment.Callbacks
                callback.onFilmSelected(item.id)
            }

            Glide.with(itemView.context)
                .load(RemoteImage(item.imageLink))
                .into(binding.filmPoster)
        }
    }

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
        holder.bind(filmsList[position])
    }

    override fun getItemCount(): Int = filmsList.size
}