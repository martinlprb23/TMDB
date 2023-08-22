package com.roblescode.tmdb.di

import com.roblescode.tmdb.constants.Constants
import com.roblescode.tmdb.data.remote.TMDBApiService
import com.roblescode.tmdb.domain.repositories.TMDBApiRepository
import com.roblescode.tmdb.domain.usecases.GetMovieById
import com.roblescode.tmdb.domain.usecases.GetMovies
import com.roblescode.tmdb.domain.usecases.UseCasesMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TmdbModule {
    // TMDB API
    @Singleton
    @Provides
    fun provideTMDBRepository(tmdbApiService: TMDBApiService) = TMDBApiRepository(tmdbApiService)

    @Singleton
    @Provides
    fun providesTMDBApiService(): TMDBApiService {

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val newUrl = originalRequest.url.newBuilder()
                .addQueryParameter("api_key", Constants.TMDB_API_KEY)
                .build()
            val newRequest: Request = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(TMDBApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUseCasesApi(repo: TMDBApiRepository) = UseCasesMovies(
        getMovies = GetMovies(repo),
        getMovieById = GetMovieById(repo)
    )

}