package com.roblescode.tmdb.ui.states

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.roblescode.tmdb.R
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.ui.components.CircularProgress
import com.roblescode.tmdb.ui.components.ShowError
import com.roblescode.tmdb.ui.viewmodels.DbViewModel
import com.roblescode.tmdb.ui.viewmodels.MovieViewModel
import com.roblescode.tmdb.ui.viewmodels.MoviesViewModel
import com.roblescode.tmdb.utils.Response


@Composable
fun GetMoviesState(
    moviesViewModel: MoviesViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val alignment =
        if (moviesViewModel.moviesList.value.isEmpty()) Alignment.Center else Alignment.BottomCenter

    when (val response = moviesViewModel.moviesResponse.value) {
        is Response.Loading -> CircularProgress(alignment = alignment)
        is Response.Success -> response.data?.let {
            LaunchedEffect(key1 = it, block = {
                moviesViewModel.incrementMovies(it)
            })
        }

        is Response.Failure -> {
            val msg = response.e?.message ?: stringResource(id = R.string.unexpected_error)
            LaunchedEffect(key1 = msg, block = {
                snackbarHostState.currentSnackbarData?.dismiss()
                val result = snackbarHostState.showSnackbar(msg, "Retry")
                if (result == SnackbarResult.ActionPerformed) moviesViewModel.retryGetMovies()
            })
        }
    }
}


@Composable
fun MovieState(
    movieId: Int,
    dbViewModel: DbViewModel,
    movieViewModel: MovieViewModel = hiltViewModel(),
    movieContent: @Composable (movie: Movie) -> Unit,
) {
    val localMovie = dbViewModel.isMovieInFavorites(movieId)
    val isFavorite = localMovie != null

    val cacheMovie = dbViewModel.getMovieDetailsFromCache()
    val isInCache = cacheMovie != null

    if (isFavorite) {
        movieContent(localMovie!!)
        dbViewModel.saveMovieInTemporalCache(localMovie)
    } else if (isInCache) {
        movieContent(cacheMovie!!)
    } else {
        movieViewModel.getMovieDetails(movieId)
        when (val response = movieViewModel.movieResponse) {
            is Response.Loading -> CircularProgress(alignment = Alignment.Center)
            is Response.Success -> response.data?.let { movieContent(it) }
            is Response.Failure -> ShowError(e = response.e){
                movieViewModel.getMovieDetails(movieId)
            }
        }
    }
}


@Composable
fun StateFavoriteMovies(
    uid: String,
    dbViewModel: DbViewModel = hiltViewModel(),
    moviesContent: @Composable (movies: List<Movie>) -> Unit
) {
    dbViewModel.getSavedMovies(uid)
    when (val response = dbViewModel.moviesResponse) {
        is Response.Loading -> CircularProgress(alignment = Alignment.Center)
        is Response.Success -> response.data?.let {
            moviesContent(it)
        }

        is Response.Failure -> ShowError(e = response.e){
            dbViewModel.getSavedMovies(uid)
        }
    }
}


@Composable
fun SaveMovieState(dbViewModel: DbViewModel, onResponse: (msg: String) -> Unit) {
    when (val response = dbViewModel.saveMovieResponse) {
        is Response.Loading -> {}
        is Response.Success -> response.data?.let {
            if (it) onResponse(stringResource(id = R.string.save_succes))
            dbViewModel.resetResponse()
        }

        is Response.Failure -> onResponse(
            response.e?.message ?: stringResource(id = R.string.unexpected_error)
        )
    }
}

@Composable
fun RemoveMovieState(dbViewModel: DbViewModel, onResponse: (msg: String) -> Unit) {
    when (val response = dbViewModel.removeMovieResponse) {
        is Response.Loading -> {}
        is Response.Success -> response.data?.let {
            if (it) onResponse(stringResource(id = R.string.remove_succes))
            dbViewModel.resetResponse()
        }

        is Response.Failure -> onResponse(
            response.e?.message ?: stringResource(id = R.string.unexpected_error)
        )
    }
}