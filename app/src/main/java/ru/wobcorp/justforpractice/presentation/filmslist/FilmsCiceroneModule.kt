package ru.wobcorp.justforpractice.presentation.filmslist

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.wobcorp.justforpractice.presentation.navigation.ScreenOpener
import ru.wobcorp.justforpractice.presentation.navigation.ScreenOpenerImpl

@Module
class FilmsCiceroneModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @FilmsScope
    fun provideCicerone(): Cicerone<Router> = cicerone

    @Provides
    @FilmsScope
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @FilmsScope
    fun provideRouter(): Router = cicerone.router

    @Provides
    @FilmsScope
    fun provideScreenOpener(): ScreenOpener = ScreenOpenerImpl()
}