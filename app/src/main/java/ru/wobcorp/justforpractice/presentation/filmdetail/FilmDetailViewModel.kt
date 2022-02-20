package ru.wobcorp.justforpractice.presentation.filmdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.usecases.GetFilmByIdUseCase
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.BaseViewState
import ru.wobcorp.justforpractice.utils.event
import ru.wobcorp.justforpractice.utils.get
import timber.log.Timber

class FilmDetailViewModel @AssistedInject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    @Assisted private val filmId: Int
) : BaseViewModel() {

    private val _state = event<BaseViewState>(BaseViewState.Loading)
    val state: StateFlow<BaseViewState>
        get() = _state

    fun getFilmById() {
        getFilmByIdUseCase.execute(filmId, FilmsLanguage.RUS)
            .doOnSubscribe { _state.value = BaseViewState.Loading }
            .get(
                disposable = disposables,
                onError = { error ->
                    Timber.d(error)
                },
                onSuccess = { filmModel ->
                    _state.value = BaseViewState.Success(filmModel)
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