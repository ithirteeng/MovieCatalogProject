package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.token.model.Token

class DeleteFromFavouritesUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        movieId: String, token: Token, completeOnError: (errorCode: Int) -> Unit
    ): Result<Boolean> {
        return try {
            val response = moviesRepository.deleteFromFavourites(movieId, token)
            if (response.isSuccessful) {
                Log.d("MOVIEREP", "deleteFavourites success")
            } else {
                Log.d("MOVIEREP", "deleteFavourites error: ${response.code()}")
                completeOnError(response.code())
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}