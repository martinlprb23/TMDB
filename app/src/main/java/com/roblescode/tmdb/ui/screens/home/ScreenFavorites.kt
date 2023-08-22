package com.roblescode.tmdb.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.tmdb.ui.components.CardMovie
import com.roblescode.tmdb.ui.components.ChangeStatusBarColor
import com.roblescode.tmdb.ui.components.SearchBar
import com.roblescode.tmdb.ui.components.TopBarFavorites
import com.roblescode.tmdb.ui.navigation.Routes
import com.roblescode.tmdb.ui.states.StateFavoriteMovies
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ScreenFavorites(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    var search by rememberSaveable { mutableStateOf("") }
    ChangeStatusBarColor(color = MaterialTheme.colorScheme.background, decorateSystem = false)

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    authViewModel.currentUser?.let { user ->
        Scaffold(
            topBar = {
                TopBarFavorites(clickBack = {
                    navController.popBackStack()
                })
            }
        ) {
            StateFavoriteMovies(uid = user.uid) { movies ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    SearchBar(search = search, onChange = { search = it })
                    LazyVerticalStaggeredGrid(
                        verticalItemSpacing = 16.dp,
                        contentPadding = PaddingValues(16.dp),
                        columns = StaggeredGridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val filterMovies = movies.filter { movie ->
                            movie.title.lowercase().replace(" ", "", ignoreCase = true)
                                .contains(search.lowercase().replace(" ", "", ignoreCase = true))
                        }
                        filterMovies.forEach { movie ->
                            item {
                                CardMovie(movie = movie) { movieId ->
                                    keyboardController?.hide()
                                    coroutineScope.launch {
                                        delay(100)
                                        navController.navigate("${Routes.ROUTE_DETAILS}/$movieId") {
                                            popUpTo(Routes.ROUTE_FAVORITES) {
                                                inclusive = false
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
