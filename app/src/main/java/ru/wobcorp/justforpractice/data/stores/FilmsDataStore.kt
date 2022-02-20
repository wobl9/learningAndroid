package ru.wobcorp.justforpractice.data.stores

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.dto.FilmDto
import ru.wobcorp.justforpractice.data.dto.FilmsSourceDto
import ru.wobcorp.justforpractice.domain.exception.NoFilmInCacheException
import ru.wobcorp.justforpractice.utils.Either
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.read
import kotlin.concurrent.write

class FilmsDataStore @Inject constructor() {

    companion object {
        private var INSTANCE: FilmsDataStore? = null
        private val LOCK = Any()

        fun getInstance(): FilmsDataStore {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val filmsDataStore = FilmsDataStore()
                INSTANCE = filmsDataStore
                return filmsDataStore
            }
        }


    }

    private val films: MutableMap<Int, FilmDto> = ConcurrentHashMap()
    private val lock = ReentrantReadWriteLock()

    fun saveFilm(film: FilmDto): Completable {
        return Completable.fromCallable {
            lock.write {
                films[film.id] = film
            }
            Completable.complete()
        }
    }

    fun saveFilmsFromSource(filmSource: FilmsSourceDto): Completable {
        return Completable.fromCallable {
            lock.write {
                filmSource.films.forEach { film ->
                    films[film.id] = film
                    Timber.d("film save ${film.id}")
                }
                Timber.d(films.toString())
            }
        }
    }

    fun getFilmById(filmId: Int): Single<Either<FilmDto, Exception>> {
        return Single.fromCallable {
            lock.read {
                films[filmId]?.let {
                    Either.Left(it)
                } ?: Either.Right(NoFilmInCacheException(filmId))
            }
        }
    }
}