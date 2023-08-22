package com.roblescode.tmdb.ui.components

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseUser
import com.roblescode.tmdb.R
import com.roblescode.tmdb.constants.Constants
import com.roblescode.tmdb.data.models.Movie


@Composable
fun ChangeStatusBarColor(color: Color, decorateSystem: Boolean) {
    val view = LocalView.current
    val systemUiController = rememberSystemUiController()
    SideEffect {
        val window = (view.context as Activity).window
        systemUiController.setSystemBarsColor(color)
        WindowCompat.setDecorFitsSystemWindows(window, decorateSystem)
    }
}

@Composable
fun ShowError(e: Exception?, onClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = e?.message ?: stringResource(id = R.string.unexpected_error),
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onClicked) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}


@Composable
fun TopContentAuth(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painterResource(id = R.drawable.tmdb_logo),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = description, color = Color.LightGray)
    }
}


@Composable
fun NavToRegister(onClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = stringResource(id = R.string.no_account), color = Color.LightGray)
            Text(
                text = stringResource(id = R.string.Sign_up),
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onClicked() }
            )
        }
    }
}


@Composable
fun NavToLogin(onClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = stringResource(id = R.string.have_account), color = Color.LightGray)
            Text(
                text = stringResource(id = R.string.log_in),
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onClicked() }
            )
        }
    }
}


@Composable
fun RatingSection(movie: Movie) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                tint = Color.Yellow
            )
            Text(
                text = movie.vote_average.toString().take(3),
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray, fontSize = 18.sp
            )
        }
        Text(
            text = stringResource(id = R.string.rating),
            color = Color.LightGray
        )
    }
}


@Composable
fun UserInfo(currentUser: FirebaseUser) {
    Row(Modifier.fillMaxWidth()) {
        AsyncImage(
            model = Constants.RANDOM_IMAGE,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = currentUser.displayName.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(text = currentUser.email.toString())
        }
    }
}
