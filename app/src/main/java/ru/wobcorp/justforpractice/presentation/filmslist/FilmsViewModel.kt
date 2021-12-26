package ru.wobcorp.justforpractice.presentation.filmslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wobcorp.justforpractice.domain.models.FilmModel
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.usecases.GetFilmsUseCase
import ru.wobcorp.justforpractice.utils.*
import javax.inject.Inject

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase
) : BaseViewModel() {

    val state = event<BaseViewState>(BaseViewState.Loading)
    val films = event(emptyList<FilmModel>())

    fun getFilms() {
        getFilmsUseCase.execute(1, FilmsLanguage.RUS)
            .get(
                disposable = disposables,
                onError = {
                    state.value = BaseViewState.Error(it)
                },
                onSuccess = {
                    state.value = BaseViewState.Success(it)
                    films.value = it.films
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