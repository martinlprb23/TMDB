package com.roblescode.tmdb.domain.repositories

import com.roblescode.tmdb.data.db.MoviesDao
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.data.models.MyMovie
import com.roblescode.tmdb.data.models.User

import com.roblescode.tmdb.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val dao: MoviesDao
) {
    suspend fun insertUser(user: User): Response<Unit> {
        return try {
            dao.insertUser(user)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    suspend fun insertMovie(movie: MyMovie): Response<Boolean> {
        return try {
            dao.insertMovie(movie)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    suspend fun deleteMovie(movie: MyMovie): Response<Boolean> {
        return try {
            dao.removeMovie(movie)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun getSavedMovies(uid: String): Flow<List<Movie>> {
        return  dao.getUserWithMovies(uid).map { userWithMoviesList ->
            userWithMoviesList.movies.map { myMovie ->
                Movie(
                    id = myMovie.id,
                    title = myMovie.title,
                    overview = myMovie.overview,
                    poster_path = myMovie.poster_path,
                    vote_average = myMovie.vote_average,
                    release_date = myMovie.release_date,
                    original_language = myMovie.original_language
                )
            }
        }
    }

}