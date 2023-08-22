package com.roblescode.tmdb.data.remote

import com.google.firebase.auth.FirebaseUser
import com.roblescode.tmdb.utils.Resource

interface AuthWithFirebase {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
}