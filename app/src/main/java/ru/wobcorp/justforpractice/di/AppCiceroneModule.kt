package ru.wobcorp.justforpractice.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.wobcorp.justforpractice.presentation.navigation.ScreenOpener
import ru.wobcorp.justforpractice.presentation.navigation.ScreenOpenerImpl
import javax.inject.Singleton

@Module
class AppCiceroneModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = cicerone

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideRouter(): Router = cicerone.router

    @Provides
    @Singleton
    fun provideScreenOpener(): ScreenOpener = ScreenOpenerImpl()
}