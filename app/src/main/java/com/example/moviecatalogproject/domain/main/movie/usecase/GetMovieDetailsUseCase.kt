package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.example.moviecatalogproject.domain.model.Token

class GetMovieDetailsUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(movieId: String, token: Token): Movie? {
        val response = moviesRepository.getMovieDetails(movieId, token)
        return if (response.isSuccessful) {
            Log.d("MOVIEREP", "getDetails success")
            response.body()
        } else {
            Log.d("MOVIEREP", "getDetails error: ${response.code()}")
            null
        }
    }

}