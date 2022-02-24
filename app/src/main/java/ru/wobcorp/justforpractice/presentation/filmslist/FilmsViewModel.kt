package ru.wobcorp.justforpractice.presentation.filmslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.usecases.GetFilmsUseCase
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.event
import ru.wobcorp.justforpractice.utils.get
import ru.wobcorp.justforpractice.utils.states.FilmsViewState
import javax.inject.Inject

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase
) : BaseViewModel() {

    companion object {
        private const val PAGE_OF_FILMS_LIST = 1
    }

    val state = event<FilmsViewState>(FilmsViewState.Loading)

    fun getFilms() {
        getFilmsUseCase.execute(PAGE_OF_FILMS_LIST, FilmsLanguage.RUS)
            .doOnSubscribe { state.value = FilmsViewState.Loading }
            .get(
                disposable = disposables,
                onError = { error ->
                    state.value = FilmsViewState.Error(error)
                },
                onSuccess = { filmsSourceModule ->
                    state.value = FilmsViewState.Success(filmsSourceModule.films)
                }
            )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getFilmsUseCase: GetFilmsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilmsViewModel(
                getFilmsUseCase = getFilmsUseCase,
            ) as T
        }
    }
}