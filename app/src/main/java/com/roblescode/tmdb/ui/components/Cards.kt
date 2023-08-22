package com.roblescode.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.roblescode.tmdb.R
import com.roblescode.tmdb.constants.Constants
import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.ui.theme.TransparentGray


// CARD MOVIE --------------------------------------->
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMovie(movie: Movie, onClicked: (Int) -> Unit) {
    Card(
        onClick = { onClicked(movie.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = Constants.POSTER_URL + movie.poster_path,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TransparentGray)
            ) {
                Text(
                    text = movie.vote_average.toString().take(3),
                    fontSize = 12.sp,
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun CardProfile(user: FirebaseUser?, clickLogout: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            user?.let { UserInfo(currentUser = it) }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = clickLogout, modifier = Modifier.fillMaxWidth()

            ) {
                Text(text = stringResource(id = R.string.logout), fontWeight = FontWeight.Bold)
            }
        }
    }
}
