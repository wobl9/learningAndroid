package ru.wobcorp.justforpractice

import android.app.Application
import ru.wobcorp.justforpractice.di.ApplicationComponent
import ru.wobcorp.justforpractice.di.DaggerApplicationComponent

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory()
            .create(this)
    }

    companion object {
        private lateinit var appComponent: ApplicationComponent
        val dagger: ApplicationComponent
            get() = appComponent
    }
}