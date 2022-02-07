package ru.wobcorp.justforpractice.domain.models

data class FilmsSourceModel(
    val page: Int,
    val totalPages: Int,
    val films: List<FilmModel>
) {
    companion object {
        private val localFilmsList = mutableListOf<FilmModel>()

        fun getFilmById(filmId: Int): FilmModel? {
            return localFilmsList.find { it.id == filmId }
        }

        fun saveFilm(film: FilmModel) {
            localFilmsList.add(film)
        }
    }
}