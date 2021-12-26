package ru.wobcorp.justforpractice.utils

sealed class BaseViewState {

    class Success<T>(data: T) : BaseViewState()
    object Loading : BaseViewState()
    class Error(error: Throwable) : BaseViewState()

}
