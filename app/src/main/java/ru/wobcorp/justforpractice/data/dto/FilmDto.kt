package ru.wobcorp.justforpractice.data.dto

import com.google.gson.annotations.SerializedName

class FilmDto(
    @SerializedName("id")val id: Int,
    @SerializedName("title")val title: String,
    @SerializedName("overview")val overview: String,
    @SerializedName("poster_path") val imageLink: String,
)