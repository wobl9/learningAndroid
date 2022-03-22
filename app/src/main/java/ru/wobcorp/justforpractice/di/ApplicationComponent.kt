package ru.wobcorp.justforpractice.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.wobcorp.justforpractice.data.remote.services.FilmsService
import ru.wobcorp.justforpractice.presentation.login.activity.LoginActivity
import ru.wobcorp.justforpractice.presentation.login.fragment.LoginFragment
import ru.wobcorp.justforpractice.presentation.login.navigation.LoginCiceroneModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        LoginCiceroneModule::class
    ]
)
interface ApplicationComponent : ApplicationDependencies {

    val context: Context
    override val remoteService: FilmsService

    fun inject(fragment: LoginFragment)
    fun inject(activity: LoginActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}