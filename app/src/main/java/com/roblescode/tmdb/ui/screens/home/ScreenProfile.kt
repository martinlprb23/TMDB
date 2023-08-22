package com.roblescode.tmdb.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.tmdb.ui.components.CardProfile
import com.roblescode.tmdb.ui.components.TopBarProfile
import com.roblescode.tmdb.ui.navigation.Routes
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile(authViewModel: AuthViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBarProfile(clickBack = {
                navController.popBackStack()
            })
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(it)
        ) {
            item {
                CardProfile(authViewModel.currentUser, clickLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.ROUTE_LOGIN) {
                        popUpTo(Routes.ROUTE_MOVIES) {
                            inclusive = true
                        }
                    }
                })
            }
        }
    }
}

