package com.roblescode.tmdb.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.roblescode.tmdb.ui.components.CardMovie
import com.roblescode.tmdb.ui.components.TopBarMovies
import com.roblescode.tmdb.ui.navigation.Routes
import com.roblescode.tmdb.ui.states.GetMoviesState
import com.roblescode.tmdb.ui.viewmodels.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScreenMovies(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel = hiltViewModel()
) {
    moviesViewModel.getMovies()
    val movies by remember { moviesViewModel.moviesList }

    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.background
    SideEffect { systemUiController.setSystemBarsColor(color) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBarMovies(
                clickProfile = {
                    navController.navigate(Routes.ROUTE_PROFILE) {
                        popUpTo(Routes.ROUTE_MOVIES) {
                            inclusive = false
                        }
                    }
                }, clickFavorites = {
                    navController.navigate(Routes.ROUTE_FAVORITES) {
                        popUpTo(Routes.ROUTE_MOVIES) {
                            inclusive = false
                        }
                    }
                })
        },
        contentWindowInsets = WindowInsets.ime,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {

        GetMoviesState(moviesViewModel, snackbarHostState)
        LazyVerticalStaggeredGrid(
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(movies) { index, movie ->
                if (index == movies.size - 1) moviesViewModel.getMovies()
                CardMovie(movie = movie) { movieId ->
                    navController.navigate("${Routes.ROUTE_DETAILS}/$movieId") {
                        popUpTo(Routes.ROUTE_MOVIES) {
                            inclusive = false
                        }
                    }
                }
            }
        }
    }
}

