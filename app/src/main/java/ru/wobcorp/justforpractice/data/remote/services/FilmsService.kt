package ru.wobcorp.justforpractice.data.remote.services

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.wobcorp.justforpractice.data.dto.FilmDto
import ru.wobcorp.justforpractice.data.dto.FilmsSourceDto

interface FilmsService {

    @GET("3/movie/popular")
    fun getPopularFilms(
        @Query("page") page: Int,
        @Query("language") language: String,
    ): Single<FilmsSourceDto>

    @GET("3/movie/{movie_id}")
    fun getFilmById(
        @Path("movie_id") id: Int,
        @Query("language") language: String
    ): Single<FilmDto>
}