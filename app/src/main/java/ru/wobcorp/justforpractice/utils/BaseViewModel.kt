package ru.wobcorp.justforpractice.utils

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        disposables.addAll(disposable)
    }

    override fun onCleared() {
        disposables.dispose()
    }
}