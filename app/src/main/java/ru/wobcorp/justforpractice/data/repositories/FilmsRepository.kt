package ru.wobcorp.justforpractice.data.repositories

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.wobcorp.justforpractice.data.mappers.FilmsMapper
import ru.wobcorp.justforpractice.data.remote.services.FilmsService
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import ru.wobcorp.justforpractice.utils.get
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val remote: FilmsService,
    private val filmsMapper: FilmsMapper
) {

    fun getFilms(page: Int, language: FilmsLanguage): Single<FilmsSourceModel> {
        return remote.getPopularFilms(page, language.query)
            .map(filmsMapper::sourceToDomain)
    }

    fun getFilmById(filmId: Int): Single<FilmModel> {
        var film = FilmsSourceModel.getFilmById(filmId)
        if (film == null) {
            getFilms(1, FilmsLanguage.RUS).get(
                disposable = CompositeDisposable(),
                onError = {},
                onSuccess = {
                    it.films.find { filmModel ->
                        filmModel.id == filmId
                    }?.let { filmModel ->
                        FilmsSourceModel.saveFilm(filmModel)
                        film = filmModel
                    }
                }
            )
        } else {
            return Single.create {
                it.onSuccess(film as @NonNull FilmModel)
            }
        }
        return Single.just(film as FilmModel)
    }
}
