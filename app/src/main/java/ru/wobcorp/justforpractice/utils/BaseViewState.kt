package ru.wobcorp.justforpractice.utils

sealed class BaseViewState {

    class Success<T>(val data: T) : BaseViewState()
    object Loading : BaseViewState()
    class Error(error: Throwable) : BaseViewState()

}
