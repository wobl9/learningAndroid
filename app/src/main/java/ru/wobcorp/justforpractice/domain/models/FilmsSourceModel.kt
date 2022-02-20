package ru.wobcorp.justforpractice.domain.models

data class FilmsSourceModel(
    val page: Int,
    val totalPages: Int,
    val films: List<FilmModel>
)