package ru.wobcorp.justforpractice.presentation.filmslist

import dagger.Component
import ru.wobcorp.justforpractice.di.ApplicationDependencies
import ru.wobcorp.justforpractice.presentation.filmdetail.FilmDetailFragment
import ru.wobcorp.justforpractice.presentation.filmsactivity.FilmsActivity
import javax.inject.Scope

@Retention
@Scope
annotation class FilmsScope

@Component(
    dependencies = [ApplicationDependencies::class]
)

@FilmsScope
interface FilmsComponent {
    fun inject(fragment: FilmsFragment)
    fun inject(fragment: FilmDetailFragment)
    fun inject(filmsActivity: FilmsActivity)

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ApplicationDependencies
        ): FilmsComponent
    }
}