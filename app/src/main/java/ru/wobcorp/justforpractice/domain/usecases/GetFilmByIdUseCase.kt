package ru.wobcorp.justforpractice.domain.usecases

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.repositories.FilmsRepository
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import javax.inject.Inject

class GetFilmByIdUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {
    fun execute(id: Int, language: FilmsLanguage): Single<FilmModel> {
        return filmsRepository.getFilmById(id, language)
    }
}