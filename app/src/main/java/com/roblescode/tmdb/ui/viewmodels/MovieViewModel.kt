package com.roblescode.tmdb.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.domain.usecases.UseCasesMovies
import com.roblescode.tmdb.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCases: UseCasesMovies
) : ViewModel() {

    var movieResponse by mutableStateOf<Response<Movie>>(Response.Loading)
        private set

    fun getMovieDetails(id: Int) = viewModelScope.launch {
        movieResponse = useCases.getMovieById(id)
    }

}