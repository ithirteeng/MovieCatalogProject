package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.model.GalleryResponse
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.model.Token

class GetMoviesListUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(moviesPage: Int, token: Token): GalleryResponse? {
        val response = moviesRepository.getMoviesList(moviesPage, token)
        return if (response.isSuccessful) {
            Log.d("MOVIEREP", "getList success")
            response.body()
        } else {
            Log.d("MOVIEREP", "getList error: ${response.code()}")
            null
        }
    }
}