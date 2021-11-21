package ru.wobcorp.justforpractice.data.mappers

import dagger.Reusable
import ru.wobcorp.justforpractice.data.dto.FilmDto
import ru.wobcorp.justforpractice.data.dto.FilmsSourceDto
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import javax.inject.Inject

@Reusable
class FilmsMapper @Inject constructor() {

    fun sourceToDomain(source: FilmsSourceDto): FilmsSourceModel = FilmsSourceModel(
        page = source.page,
        totalPages = source.totalPages,
        films = source.films.map { filmDto -> filmToDomain(filmDto) }
    )

    fun filmToDomain(film: FilmDto): FilmModel = FilmModel(
        id = film.id,
        title = film.title,
        overview = film.overview,
        imageLink = film.imageLink
    )
}