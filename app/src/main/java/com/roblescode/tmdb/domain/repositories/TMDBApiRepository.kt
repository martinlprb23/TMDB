package com.roblescode.tmdb.domain.repositories


import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.data.models.responses.MovieResponse
import com.roblescode.tmdb.data.remote.TMDBApiService
import com.roblescode.tmdb.utils.Response
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class TMDBApiRepository @Inject constructor(
    private val tmdbApiService: TMDBApiService
) {
    suspend fun getPopularMovies(page: Int): Response<MovieResponse> {
        return try {
            val response = tmdbApiService.getPopularMovies(page)
            Response.Success(response)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    suspend fun getMovieById(movieId: Int): Response<Movie> {
        return try {
            val response = tmdbApiService.getMovieById(movieId)
            Response.Success(response)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}
