package com.roblescode.tmdb.ui.states

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roblescode.tmdb.ui.components.MiniProgressBar
import com.roblescode.tmdb.ui.viewmodels.AuthViewModel
import com.roblescode.tmdb.ui.viewmodels.DbViewModel
import com.roblescode.tmdb.utils.Resource

@Composable
fun LoginState(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    dbViewModel: DbViewModel = hiltViewModel(),
    navigateMovies: () -> Unit
) {

    val loginFlow = authViewModel.loginFlow.collectAsState()
    loginFlow.value?.let {
        when (it) {
            is Resource.Loading -> MiniProgressBar(Modifier.padding(bottom = 56.dp))
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    dbViewModel.saveUser(it.result.uid)
                    navigateMovies()
                }
            }

            is Resource.Failure -> {
                LaunchedEffect(key1 = it, block = {
                    snackbarHostState.showSnackbar(
                        it.exception.message.toString()
                    )
                })
            }
        }
    }
}

@Composable
fun RegisterState(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    dbViewModel: DbViewModel = hiltViewModel(),
    navigateMovies: () -> Unit
) {
    val signupFlow = authViewModel.signupFlow.collectAsState()

    signupFlow.value?.let { resource ->
        when (resource) {
            is Resource.Loading -> MiniProgressBar(Modifier.padding(bottom = 56.dp))
            is Resource.Success -> resource.result.uid.let { uid ->
                LaunchedEffect(key1 = uid, block = {
                    dbViewModel.saveUser(uid)
                    authViewModel.resetSignup()
                    navigateMovies()
                })
            }

            is Resource.Failure -> {
                val msg = resource.exception.message
                LaunchedEffect(key1 = msg, block = {
                    snackbarHostState.showSnackbar(msg.toString(), "Accept")
                    authViewModel.resetSignup()
                })
            }
        }
    }
}