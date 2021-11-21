package ru.wobcorp.justforpractice.presentation.filmslist

import dagger.Component
import ru.wobcorp.justforpractice.di.ApplicationDependencies

@Component(
    dependencies = [ApplicationDependencies::class],
)
interface FilmsComponent {
    fun inject(fragment: FilmsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ApplicationDependencies
        ): FilmsComponent
    }
}