package ru.wobcorp.justforpractice.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Qualifier

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val authedUrl = originalRequest
            .url()
            .newBuilder()
            .addQueryParameter(API_KEY, STATIC_KEY)
            .build()

        return originalRequest.newBuilder()
            .url(authedUrl)
            .build()
            .let(chain::proceed)
    }

    private companion object {
        const val API_KEY = "api_key"
        const val STATIC_KEY = "c355a0993386aa6279e2f6f483182b48"
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Auth