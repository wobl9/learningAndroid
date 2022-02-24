package ru.wobcorp.justforpractice.utils.states

import ru.wobcorp.justforpractice.domain.models.FilmModel

sealed class FilmDetailViewState {

    class Success(val data: FilmModel) : FilmDetailViewState()
    object Loading : FilmDetailViewState()
    class Error(error: Throwable) : FilmDetailViewState()

}
