package com.roblescode.tmdb.data.models.responses

import androidx.room.Embedded
import androidx.room.Relation
import com.roblescode.tmdb.data.models.MyMovie
import com.roblescode.tmdb.data.models.User

data class UserWithMovies(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "userId"
    )
    val movies: List<MyMovie>
)

