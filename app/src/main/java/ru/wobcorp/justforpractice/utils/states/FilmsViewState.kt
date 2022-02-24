package ru.wobcorp.justforpractice.utils.states

import ru.wobcorp.justforpractice.domain.models.FilmModel

sealed class FilmsViewState {

    class Success(val data: List<FilmModel>) : FilmsViewState()
    object Loading : FilmsViewState()
    class Error(error: Throwable) : FilmsViewState()

}
