package com.roblescode.tmdb.domain.usecases

import com.roblescode.tmdb.domain.repositories.TMDBApiRepository

data class UseCasesMovies(
    val getMovies: GetMovies,
    val getMovieById: GetMovieById
)

class GetMovies(private val repository: TMDBApiRepository) {
    suspend operator fun invoke(page: Int)= repository.getPopularMovies(page = page)
}

class GetMovieById(private val repository: TMDBApiRepository) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieById(movieId)
}
