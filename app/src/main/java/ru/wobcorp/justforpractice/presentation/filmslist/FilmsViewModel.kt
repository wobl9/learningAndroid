package ru.wobcorp.justforpractice.presentation.filmslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.StateFlow
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.usecases.GetFilmsUseCase
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.BaseViewState
import ru.wobcorp.justforpractice.utils.event
import ru.wobcorp.justforpractice.utils.get
import javax.inject.Inject

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase
) : BaseViewModel() {

    private val _state = event<BaseViewState>(BaseViewState.Loading)
    val state: StateFlow<BaseViewState>
        get() = _state

    fun getFilms() {
        getFilmsUseCase.execute(1, FilmsLanguage.RUS)
            .get(
                disposable = disposables,
                onError = {
                    _state.value = BaseViewState.Error(it)
                },
                onSuccess = {
                    _state.value = BaseViewState.Success(it.films)
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