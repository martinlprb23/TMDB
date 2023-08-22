package com.roblescode.tmdb.ui.screens.auth


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.tmdb.R
import com.roblescode.tmdb.ui.components.FormRegister
import com.roblescode.tmdb.ui.components.NavToLogin
import com.roblescode.tmdb.ui.components.TopBarRegister
import com.roblescode.tmdb.ui.components.TopContentAuth
import com.roblescode.tmdb.ui.navigation.Routes
import com.roblescode.tmdb.ui.states.RegisterState
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopBarRegister {
                navController.popBackStack()
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TopContentAuth(title = stringResource(id = R.string.new_user), description = stringResource(
                    id = R.string.create_account
                ) )

                Spacer(modifier = Modifier.height(32.dp))
                FormRegister(onSubmit = { name, email, password ->
                    authViewModel.signup(name, email, password)
                })

                NavToLogin {
                    navController.popBackStack()
                }
            }
            RegisterState(authViewModel, snackbarHostState, navigateMovies = {
                navController.navigate(Routes.ROUTE_MOVIES) {
                    popUpTo(Routes.ROUTE_LOGIN) {
                        inclusive = true
                    }
                }
            })
        }
    }
}
