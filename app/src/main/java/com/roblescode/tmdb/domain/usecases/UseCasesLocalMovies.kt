package com.roblescode.tmdb.domain.usecases


import com.roblescode.tmdb.data.models.Movie
import com.roblescode.tmdb.data.models.MyMovie
import com.roblescode.tmdb.data.models.User
import com.roblescode.tmdb.domain.repositories.MoviesRepositoryImpl
import com.roblescode.tmdb.utils.Response
import kotlinx.coroutines.flow.Flow

data class UseCasesLocalMovies(
    val insertUser: InsertUser,
    val insertMovie: InsertMovie,
    val deleteMovie: DeleteMovie,
    val getLocalMovies: GetLocalMovies
)

class InsertUser(private val repo: MoviesRepositoryImpl) {
    suspend operator fun invoke(user: User): Response<Unit> {
        return repo.insertUser(user)
    }
}

class InsertMovie(private val repo: MoviesRepositoryImpl) {
    suspend operator fun invoke(movie: MyMovie): Response<Boolean> {
        return repo.insertMovie(movie)
    }
}

class DeleteMovie(private val repo: MoviesRepositoryImpl) {
    suspend operator fun invoke(movie: MyMovie): Response<Boolean> {
        return repo.deleteMovie(movie)
    }
}

class GetLocalMovies(private val repo: MoviesRepositoryImpl) {
    operator fun invoke(uid: String): Flow<List<Movie>> {
        return repo.getSavedMovies(uid = uid)
    }
}
