package com.roblescode.tmdb.ui.screens.auth


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.tmdb.R
import com.roblescode.tmdb.ui.navigation.Routes
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel

@Composable
fun SplashScreen(authViewModel: AuthViewModel, navController: NavHostController) {
    val currentUser = authViewModel.currentUser

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.app_name))
    }

    LaunchedEffect(key1 = currentUser, block = {
        if (currentUser == null)
            navController.navigate(Routes.ROUTE_LOGIN) {
                popUpTo(Routes.ROUTE_SPLASH) {
                    inclusive = true
                }
            }
        else
            navController.navigate(Routes.ROUTE_MOVIES) {
                popUpTo(Routes.ROUTE_SPLASH) {
                    inclusive = true
                }
            }
    })
}