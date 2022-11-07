package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.model.Token

class DeleteFromFavouritesUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        completeOnError: (errorCode: Int) -> Unit
    ) {
        val response = moviesRepository.deleteFromFavourites(movieId, token)
        if (response.isSuccessful) {
            Log.d("REVIEW", "delete favourites success")
        } else {
            Log.d("REVIEW", "delete favourites error: ${response.code()}")
            completeOnError(response.code())
        }
    }
}