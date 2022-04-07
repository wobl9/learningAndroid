package ru.wobcorp.justforpractice.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.wobcorp.justforpractice.utils.preferences.SPrefHelper
import ru.wobcorp.justforpractice.utils.preferences.SPrefHelperImpl
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun providePreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSPrefHelper(sharedPreferences: SharedPreferences): SPrefHelper {
        return SPrefHelperImpl(sharedPreferences)
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "app_prefs"
    }
}