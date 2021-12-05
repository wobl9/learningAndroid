package ru.wobcorp.justforpractice.domain.usecases

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.repositories.FilmsRepository
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import javax.inject.Inject

class GetFilmsUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository,
) {
    fun execute(page: Int, language: FilmsLanguage): Single<FilmsSourceModel> {
        return filmsRepository.getFilms(page, language)
    }
}