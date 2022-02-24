package ru.wobcorp.justforpractice.presentation.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.usecases.GetFilmByIdUseCase
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.event
import ru.wobcorp.justforpractice.utils.get
import ru.wobcorp.justforpractice.utils.states.FilmDetailViewState

class FilmDetailViewModel @AssistedInject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    @Assisted private val filmId: Int
) : BaseViewModel() {

    val state = event<FilmDetailViewState>(FilmDetailViewState.Loading)

    fun getFilmById() {
        getFilmByIdUseCase.execute(filmId, FilmsLanguage.RUS)
            .doOnSubscribe { state.value = FilmDetailViewState.Loading }
            .get(
                disposable = disposables,
                onError = { error ->
                    state.value = FilmDetailViewState.Error(error)
                },
                onSuccess = { filmModel ->
                    state.value = FilmDetailViewState.Success(filmModel)
                }
            )
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(filmId: Int): FilmDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            filmId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(filmId) as T
            }
        }
    }
}