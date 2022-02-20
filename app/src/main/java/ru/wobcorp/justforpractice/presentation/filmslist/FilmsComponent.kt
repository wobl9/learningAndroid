package ru.wobcorp.justforpractice.presentation.filmslist

import dagger.Component
import dagger.Module
import ru.wobcorp.justforpractice.data.stores.FilmsDataStore
import ru.wobcorp.justforpractice.di.ApplicationDependencies
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import javax.inject.Singleton

@Component(
    dependencies = [ApplicationDependencies::class],
    modules = [FilmsComponent.FilmsListModule::class]
)
interface FilmsComponent {
    fun inject(fragment: FilmsFragment)
    fun inject(fragment: FilmDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ApplicationDependencies
        ): FilmsComponent
    }

    @Module
    abstract class FilmsListModule {
        @Singleton
        abstract fun provideFilmsDataStore(): FilmsDataStore
    }
}