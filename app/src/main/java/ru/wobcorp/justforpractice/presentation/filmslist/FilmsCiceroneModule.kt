package ru.wobcorp.justforpractice.presentation.filmslist

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.wobcorp.justforpractice.presentation.navigation.FilmsScreenOpener
import ru.wobcorp.justforpractice.presentation.navigation.FilmsScreenOpenerImpl

@Module
class FilmsCiceroneModule {

    @Provides
    @FilmsScope
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @FilmsScope
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    @FilmsScope
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides
    @FilmsScope
    fun provideScreenOpener(): FilmsScreenOpener {
        return FilmsScreenOpenerImpl()
    }
}