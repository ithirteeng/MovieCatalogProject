package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.MovieDetails

class GetMovieDetailsUseCase {

    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<MovieDetails?> {
        return try {
            val response = moviesRepository.getMovieDetails(movieId, token)
            if (response.isSuccessful) {
                Log.d("MOVIEREP", "get details success")
                Result.success(response.body())
            } else {
                completeOnError(response.code())
                Log.d("MOVIEREP", "get details error : ${response.code()}")
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}
