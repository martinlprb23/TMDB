package com.roblescode.tmdb.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.roblescode.tmdb.R
import com.roblescode.tmdb.constants.Constants
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.ui.components.ChangeStatusBarColor
import com.roblescode.tmdb.ui.components.RatingSection
import com.roblescode.tmdb.ui.states.MovieState
import com.roblescode.tmdb.ui.states.RemoveMovieState
import com.roblescode.tmdb.ui.states.SaveMovieState
import com.roblescode.tmdb.ui.theme.TransparentGray
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel
import com.roblescode.tmdb.ui.viewmodels.DbViewModel
import com.roblescode.tmdb.ui.viewmodels.PaletteViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDetails(
    movieId: Int,
    authViewModel: AuthViewModel,
    navController: NavHostController,
    dbViewModel: DbViewModel = hiltViewModel()
) {

    authViewModel.currentUser?.uid?.let { uid ->
        dbViewModel.getSavedMovies(uid)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val topContentHeight = (screenHeight * 0.70).toInt().dp
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }

    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        MovieState(movieId = movieId, dbViewModel = dbViewModel) { movie ->
            ChangeStatusBarColor(color = Color.Transparent, decorateSystem = false)
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.background, dominantColor)
                        )
                    )
            ) {
                TopMovieContent(
                    movie = movie,
                    modifier = Modifier.height(topContentHeight),
                    onBackClick = { navController.popBackStack() },
                    dominantColor = { dominantColor = it })

                DetailMovieContent(
                    movie = movie,
                    isFavorite = dbViewModel.isMovieFavorite,
                    onFavoriteClicked = {
                        authViewModel.currentUser?.uid?.let { uid ->
                            if (dbViewModel.isMovieFavorite) dbViewModel.removeMovie(uid, movie)
                            else dbViewModel.saveMovie(uid, movie)
                        }
                    })
            }
            SaveMovieState(dbViewModel = dbViewModel, onResponse = { msg ->
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch { snackbarHostState.showSnackbar(msg) }
            })
            RemoveMovieState(dbViewModel = dbViewModel, onResponse = { msg ->
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch { snackbarHostState.showSnackbar(msg) }
            })
        }
    }
}

@Composable
fun TopMovieContent(
    movie: Movie,
    modifier: Modifier,
    paletteViewModel: PaletteViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    dominantColor: (Color) -> Unit
) {

    val painter = rememberAsyncImagePainter(model =  Constants.POSTER_URL + movie.poster_path)
    var painterState by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model =  Constants.POSTER_URL + movie.poster_path,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onLoading = { painterState = false },
            onSuccess = { painterState = true }
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp, vertical = 56.dp)
                .clip(CircleShape)
                .background(TransparentGray)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }

    if (painterState) {
        LaunchedEffect(key1 = painter, block = {
            paletteViewModel.calcDominantColor(painter) {
                dominantColor(it)
            }
        })
    }
}

@Composable
fun DetailMovieContent(movie: Movie, isFavorite: Boolean, onFavoriteClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.White
                )

                Text(
                    text = "${movie.release_date} | ${movie.original_language}",
                    color = Color.LightGray
                )

            }
            IconButton(onClick = onFavoriteClicked) {
                val painter =
                    if (isFavorite) painterResource(id = R.drawable.bookmark_fill)
                    else painterResource(id = R.drawable.bookmark)
                Icon(
                    painter = painter,
                    contentDescription = "Save",
                    tint = Color.LightGray,
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Divider(color = TransparentGray)
            RatingSection(movie = movie)
            Divider(color = TransparentGray)
        }

        Text(
            color = Color.LightGray,
            text = movie.overview,
            textAlign = TextAlign.Justify,
        )
    }


}
