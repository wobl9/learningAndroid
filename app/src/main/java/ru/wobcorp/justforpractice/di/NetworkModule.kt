package ru.wobcorp.justforpractice.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.wobcorp.justforpractice.data.remote.interceptors.Auth
import ru.wobcorp.justforpractice.data.remote.interceptors.AuthInterceptor
import ru.wobcorp.justforpractice.data.remote.interceptors.Chucker
import ru.wobcorp.justforpractice.data.remote.services.FilmsService
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttp(client: OkHttpClient): FilmsService {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
            .create(FilmsService::class.java)
    }

    @Provides
    @Singleton
    fun provideClient(
        @Auth authInterceptor: Interceptor,
        @Chucker chucker: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(chucker)
            .build()
    }

    @Provides
    @Chucker
    @Singleton
    fun provideChucker(context: Context): Interceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Auth
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return AuthInterceptor()
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
    }
}