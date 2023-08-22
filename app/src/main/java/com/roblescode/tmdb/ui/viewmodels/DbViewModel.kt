package com.roblescode.tmdb.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.data.models.MyMovie
import com.roblescode.tmdb.data.models.User
import com.roblescode.tmdb.domain.usecases.UseCasesLocalMovies
import com.roblescode.tmdb.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(
    private val useCases: UseCasesLocalMovies
) : ViewModel() {

    var isMovieFavorite by mutableStateOf(false)
        private set

    fun saveUser(uid: String) = viewModelScope.launch {
        val newUser = User(uid)
        useCases.insertUser(newUser)
    }

    private fun createMyMovie(uid: String, movie: Movie): MyMovie {
        return MyMovie(
            userId = uid,
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            poster_path = movie.poster_path,
            vote_average = movie.vote_average,
            release_date = movie.release_date,
            original_language = movie.original_language
        )
    }

    var saveMovieResponse by mutableStateOf<Response<Boolean>>(Response.Success(null))
        private set

    fun saveMovie(uid: String, movie: Movie) = viewModelScope.launch {
        val newMovie = createMyMovie(uid, movie)
        saveMovieResponse = Response.Loading
        saveMovieResponse = useCases.insertMovie(newMovie)
        if (saveMovieResponse is Response.Success) {
            isMovieFavorite = true
            moviesList.value = moviesList.value + movie /* Add in temporal local list */
        }
    }

    var removeMovieResponse by mutableStateOf<Response<Boolean>>(Response.Success(null))
        private set

    fun removeMovie(uid: String, movie: Movie) = viewModelScope.launch {
        val newMovie = createMyMovie(uid, movie)
        removeMovieResponse = Response.Loading
        removeMovieResponse = useCases.deleteMovie(newMovie)
        if (removeMovieResponse is Response.Success) {
            isMovieFavorite = false
            moviesList.value = moviesList.value.filter { it.id != movie.id } // Eliminar la película de la lista local
        }
    }


    private var moviesList = mutableStateOf<List<Movie>>(listOf())
    var moviesResponse by mutableStateOf<Response<List<Movie>>>(Response.Loading)
        private set

    fun getSavedMovies(uid: String) = viewModelScope.launch {
        useCases.getLocalMovies(uid).collect { movies ->
            val sortedMovies = movies.sortedBy { it.title }
            moviesList.value = sortedMovies
            moviesResponse = Response.Success(sortedMovies)
        }
    }

    fun isMovieInFavorites(movieId: Int): Movie? {
        val movies = moviesList.value
        val exists = movies.find { it.id == movieId }
        isMovieFavorite = exists != null
        return exists
    }

    fun resetResponse(){
        saveMovieResponse = Response.Success(null)
        removeMovieResponse = Response.Success(null)
    }

    private val movieDetailsCache = mutableStateOf<Movie?>(null)
    fun saveMovieInTemporalCache(movie: Movie) {
        movieDetailsCache.value = movie
    }

    fun getMovieDetailsFromCache(): Movie? {
        return movieDetailsCache.value
    }
}