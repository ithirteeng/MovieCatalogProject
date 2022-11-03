package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.model.Token

class AddToFavouritesUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(movieId: String, token: Token) {
        val response = moviesRepository.postFavouritesAddedData(movieId, token)
        if (response.isSuccessful) {
            Log.d("MOVIEREP", "addFavourites success")
        } else {
            Log.d("MOVIEREP", "addFavourites error: ${response.code()}")
        }
    }
}