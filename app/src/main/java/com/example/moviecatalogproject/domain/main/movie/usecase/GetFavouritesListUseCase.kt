package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.example.moviecatalogproject.domain.model.Token

class GetFavouritesListUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(token: Token): ArrayList<Movie>? {
        val response = moviesRepository.getFavouritesList(token)
        return if (response.isSuccessful) {
            Log.d("MOVIEREP", "getFavourites success")
            response.body()?.movies
        } else {
            Log.d("MOVIEREP", "getFavourites error: ${response.code()}")
            null
        }
    }
}