package com.roblescode.tmdb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.roblescode.tmdb.ui.screens.auth.LoginScreen
import com.roblescode.tmdb.ui.screens.auth.RegisterScreen
import com.roblescode.tmdb.ui.screens.auth.SplashScreen
import com.roblescode.tmdb.ui.screens.home.ScreenDetails
import com.roblescode.tmdb.ui.screens.home.ScreenFavorites
import com.roblescode.tmdb.ui.screens.home.ScreenMovies
import com.roblescode.tmdb.ui.screens.home.ScreenProfile
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel

@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    navController: NavHostController,
    startDestination: String = Routes.ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.ROUTE_SPLASH) {
            SplashScreen(authViewModel, navController)
        }

        composable(Routes.ROUTE_LOGIN) {
            LoginScreen(authViewModel, navController)
        }

        composable(Routes.ROUTE_REGISTER) {
            RegisterScreen(authViewModel, navController)
        }

        composable(Routes.ROUTE_PROFILE) {
            ScreenProfile(authViewModel, navController)
        }

        composable(Routes.ROUTE_MOVIES) {
            ScreenMovies(navController)
        }

        composable(Routes.ROUTE_FAVORITES) {
            ScreenFavorites(authViewModel, navController)
        }

        composable(
            route = "${Routes.ROUTE_DETAILS}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = remember { it.arguments?.getInt("movieId") }
            movieId?.let {
                ScreenDetails(
                    movieId = movieId,
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
        }

    }
}