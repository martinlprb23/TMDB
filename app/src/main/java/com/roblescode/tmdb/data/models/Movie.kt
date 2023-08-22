package com.roblescode.tmdb.data.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
    val release_date: String,
    val original_language: String
)