package ru.wobcorp.justforpractice.presentation.filmslist

import dagger.Component
import ru.wobcorp.justforpractice.di.ApplicationDependencies
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment

@Component(
    dependencies = [ApplicationDependencies::class],
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
}