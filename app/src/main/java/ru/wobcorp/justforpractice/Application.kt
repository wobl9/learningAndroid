package ru.wobcorp.justforpractice

import android.app.Application
import ru.wobcorp.justforpractice.di.ApplicationComponent
import ru.wobcorp.justforpractice.di.DaggerApplicationComponent
import timber.log.Timber

class Application : Application() {

    companion object {
        private lateinit var appComponent: ApplicationComponent
        val dagger: ApplicationComponent
            get() = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerApplicationComponent.factory()
            .create(this)
    }
}