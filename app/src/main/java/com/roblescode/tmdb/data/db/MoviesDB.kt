package com.roblescode.tmdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roblescode.tmdb.data.models.MyMovie
import com.roblescode.tmdb.data.models.User

@Database(
    entities = [MyMovie::class, User::class],
    version = 1,
    exportSchema = false
)

abstract class MoviesDB : RoomDatabase() {
    abstract val moviesDao: MoviesDao

    companion object {
        const val DATABASE_NAME = "movies"
    }
}