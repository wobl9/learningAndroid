package ru.wobcorp.justforpractice.utils

interface ComponentProvider<T> {
    fun provideComponent(): T
}