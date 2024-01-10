package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.token.model.Token

class AddToFavouritesUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<Boolean> {
        return try {
            val response = moviesRepository.addToFavourites(movieId, token)
            if (response.isSuccessful) {
                Log.d("REVIEW", "add favourites success")
            } else {
                Log.d("REVIEW", "add favourites error: ${response.code()}")
                completeOnError(response.code())
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}