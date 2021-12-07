package ru.wobcorp.justforpractice.presentation.filmslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wobcorp.justforpractice.domain.models.FilmsLanguage
import ru.wobcorp.justforpractice.domain.models.FilmsSourceModel
import ru.wobcorp.justforpractice.domain.usecases.GetFilmsUseCase
import ru.wobcorp.justforpractice.utils.BaseViewModel
import ru.wobcorp.justforpractice.utils.event
import ru.wobcorp.justforpractice.utils.get
import timber.log.Timber
import javax.inject.Inject

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase
) : BaseViewModel() {

    val films = event(emptyList<FilmsSourceModel>())
    val test = 0


    fun getFilms() {
        getFilmsUseCase.execute(1, FilmsLanguage.RUS)
            .get(disposables) { filmsSourceModel ->
                Timber.d(filmsSourceModel.toString())
            }
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