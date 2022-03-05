package ru.wobcorp.justforpractice.data.stores

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.dto.FilmDto
import ru.wobcorp.justforpractice.data.dto.FilmsSourceDto
import ru.wobcorp.justforpractice.domain.exception.NoFilmInCacheException
import ru.wobcorp.justforpractice.presentation.filmslist.FilmsScope
import ru.wobcorp.justforpractice.utils.Either
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@FilmsScope
class FilmsDataStore @Inject constructor() {

    private val films: MutableMap<Int, FilmDto> = ConcurrentHashMap()

    fun saveFilm(film: FilmDto): Completable {
        return Completable.fromCallable {
            films[film.id] = film
            Completable.complete()
        }
    }

    fun saveFilmsFromSource(filmSource: FilmsSourceDto): Completable {
        return Completable.fromCallable {
            filmSource.films.forEach { film ->
                films[film.id] = film
            }
            Completable.complete()
        }
    }

    fun getFilmById(filmId: Int): Single<Either<FilmDto, Exception>> {
        return Single.fromCallable {
            films[filmId]?.let {
                Either.Left(it)
            } ?: Either.Right(NoFilmInCacheException(filmId))
        }
    }
}