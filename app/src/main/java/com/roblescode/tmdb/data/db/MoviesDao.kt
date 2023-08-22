package com.roblescode.tmdb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.roblescode.tmdb.data.models.MyMovie
import com.roblescode.tmdb.data.models.User
import com.roblescode.tmdb.data.models.responses.UserWithMovies
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Transaction
    @Query("SELECT * FROM users WHERE uid = :userId")
    fun getUserWithMovies(userId: String): Flow<UserWithMovies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MyMovie)

    @Delete
    suspend fun removeMovie(movie: MyMovie)
}
