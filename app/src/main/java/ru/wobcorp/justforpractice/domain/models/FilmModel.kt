package ru.wobcorp.justforpractice.domain.models

data class FilmModel(
    val id: Int,
    val title: String,
    val overview: String,
    val imageLink: String
)
