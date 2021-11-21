package ru.wobcorp.justforpractice.data.repositories

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.mappers.FilmsMapper
import ru.wobcorp.justforpractice.data.remote.services.FilmsService
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val remote: FilmsService,
    private val filmsMapper: FilmsMapper
) {

    fun getFilms(page: Int, language: FilmsLanguage): Single<FilmsSourceModel> {
        return remote.getPopularFilms(page, language.query)
            .map(filmsMapper::sourceToDomain)
    }
}