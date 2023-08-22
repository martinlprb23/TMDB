package com.roblescode.tmdb.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.roblescode.tmdb.R
import com.roblescode.tmdb.constants.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMovies(clickProfile: () -> Unit, clickFavorites: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.movies),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        AsyncImage(
            model =  Constants.RANDOM_IMAGE,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(30.dp)
                .clip(CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
                .clickable { clickProfile() }
        )
    }, actions = {
        IconButton(onClick = clickFavorites) {
            Icon(painterResource(id = R.drawable.bookmark), contentDescription = null)
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFavorites(clickBack: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.favorites),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = clickBack) {
            Icon(painterResource(id = R.drawable.back), contentDescription = null)
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(clickBack: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.profile),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = clickBack) {
            Icon(painterResource(id = R.drawable.back), contentDescription = null)
        }
    })
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRegister(clickBack: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.register),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = clickBack) {
            Icon(painterResource(id = R.drawable.back), contentDescription = null)
        }
    })
}
