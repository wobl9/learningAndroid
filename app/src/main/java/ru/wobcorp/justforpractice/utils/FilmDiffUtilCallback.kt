package ru.wobcorp.justforpractice.utils

import androidx.recyclerview.widget.DiffUtil
import ru.wobcorp.justforpractice.domain.models.FilmModel

class FilmDiffUtilCallback() : DiffUtil.ItemCallback<FilmModel>() {

    override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
        return oldItem == newItem
    }
}