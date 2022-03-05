package ru.wobcorp.justforpractice.data.repositories

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.dto.FilmDto
import ru.wobcorp.justforpractice.data.mappers.FilmsMapper
import ru.wobcorp.justforpractice.data.remote.services.FilmsService
import ru.wobcorp.justforpractice.data.stores.FilmsDataStore
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import ru.wobcorp.justforpractice.utils.Either
import timber.log.Timber
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val remote: FilmsService,
    private val filmsMapper: FilmsMapper,
    private val filmsDataStore: FilmsDataStore
) {

    fun getFilms(page: Int, language: FilmsLanguage): Single<FilmsSourceModel> {
        return remote.getPopularFilms(page, language.query)
            .flatMap { filmDto ->
                filmsDataStore.saveFilmsFromSource(filmDto).andThen(Single.just(filmDto))
            }
            .map(filmsMapper::sourceToDomain)
    }

    fun getFilmById(filmId: Int, language: FilmsLanguage): Single<FilmModel> {
        return filmsDataStore.getFilmById(filmId).flatMap { result ->
            when (result) {
                is Either.Left -> {
                    Single.just(result.l)
                }
                is Either.Right -> {
                    getRemoteFilmById(filmId, language)
                }
            }.map(filmsMapper::filmToDomain)
        }
    }

    private fun getRemoteFilmById(filmId: Int, language: FilmsLanguage): Single<FilmDto> {
        return remote.getFilmById(filmId, language.query)
    }
}
