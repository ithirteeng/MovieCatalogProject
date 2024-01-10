package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.token.model.Token

class DeleteFromFavouritesUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<Boolean> {
        return try {
            val response = moviesRepository.deleteFromFavourites(movieId, token)
            if (response.isSuccessful) {
                Log.d("REVIEW", "delete favourites success")
                Result.success(true)
            } else {
                Log.d("REVIEW", "delete favourites error: ${response.code()}")
                completeOnError(response.code())
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}