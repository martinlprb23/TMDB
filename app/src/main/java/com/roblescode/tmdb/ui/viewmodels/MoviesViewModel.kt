package com.roblescode.tmdb.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.data.models.responses.MovieResponse
import com.roblescode.tmdb.domain.usecases.UseCasesMovies
import com.roblescode.tmdb.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val useCasesMovies: UseCasesMovies
) : ViewModel() {

    private var curPage = 1
    var moviesList = mutableStateOf<List<Movie>>(listOf())
        private set

    private val _moviesResponse = mutableStateOf<Response<MovieResponse>>(Response.Success(null))
    val moviesResponse: State<Response<MovieResponse>> = _moviesResponse

    fun getMovies() = viewModelScope.launch {
        _moviesResponse.value = Response.Loading
        _moviesResponse.value = useCasesMovies.getMovies(curPage)
    }

    fun incrementMovies(movieResponse: MovieResponse) {
        val newMovies = movieResponse.results.filterNot { newMovie ->
            moviesList.value.any { existingMovie -> existingMovie.id == newMovie.id }
        }
        moviesList.value = moviesList.value + newMovies
        curPage++
    }

    fun retryGetMovies() {
        getMovies()
    }


}