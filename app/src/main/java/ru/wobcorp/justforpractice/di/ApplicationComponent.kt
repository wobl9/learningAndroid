package ru.wobcorp.justforpractice.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.wobcorp.justforpractice.data.remote.services.FilmsService
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
    ]
)
interface ApplicationComponent : ApplicationDependencies {

    val context: Context
    override val remoteService: FilmsService

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}