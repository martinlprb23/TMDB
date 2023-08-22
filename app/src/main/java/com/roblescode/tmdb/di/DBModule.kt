package com.roblescode.tmdb.di

import android.app.Application
import androidx.room.Room
import com.roblescode.tmdb.data.db.MoviesDB
import com.roblescode.tmdb.domain.repositories.MoviesRepositoryImpl
import com.roblescode.tmdb.domain.usecases.DeleteMovie
import com.roblescode.tmdb.domain.usecases.GetLocalMovies
import com.roblescode.tmdb.domain.usecases.InsertMovie
import com.roblescode.tmdb.domain.usecases.InsertUser
import com.roblescode.tmdb.domain.usecases.UseCasesLocalMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDB {
        return Room.databaseBuilder(app, MoviesDB::class.java, MoviesDB.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(db: MoviesDB): MoviesRepositoryImpl {
        return MoviesRepositoryImpl(db.moviesDao)
    }

    @Provides
    @Singleton
    fun providesMoviesUseCases(repository: MoviesRepositoryImpl): UseCasesLocalMovies {
        return UseCasesLocalMovies(
            insertUser = InsertUser(repository),
            insertMovie = InsertMovie(repository),
            deleteMovie = DeleteMovie(repository),
            getLocalMovies = GetLocalMovies(repository)
        )
    }

}