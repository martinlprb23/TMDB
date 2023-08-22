package com.roblescode.tmdb.data.models.responses

import com.roblescode.tmdb.data.models.Movie

data class MovieResponse(
    val results: List<Movie>
)