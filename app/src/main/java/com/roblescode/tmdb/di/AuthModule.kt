package com.roblescode.tmdb.di

import com.google.firebase.auth.FirebaseAuth
import com.roblescode.tmdb.data.remote.AuthWithFirebase
import com.roblescode.tmdb.domain.repositories.AuthWithFirebaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    // FIREBASE AUTH
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthWithFirebaseImpl): AuthWithFirebase = impl

}