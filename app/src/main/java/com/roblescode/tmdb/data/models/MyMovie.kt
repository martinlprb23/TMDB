package com.roblescode.tmdb.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_movies")
data class MyMovie(
    @PrimaryKey val id: Int,
    val userId: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
    val release_date: String,
    val original_language: String
)
