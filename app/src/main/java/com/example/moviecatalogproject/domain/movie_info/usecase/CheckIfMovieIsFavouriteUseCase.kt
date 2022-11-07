package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.movie.helper.MovieMapper
import com.example.moviecatalogproject.presentation.main.movie.model.FavouriteMovie

class CheckIfMovieIsFavouriteUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        completeOnError: (errorCode: Int) -> Unit
    ): Boolean {
        val response = moviesRepository.getFavouritesList(token)
        return if (response.isSuccessful) {
            Log.d("REVIEW", "getFavourites success")
            val list = MovieMapper.favouritesResponseToFavouritesList(response.body()!!)
            list.contains(movieId)
        } else {
            Log.d("REVIEW", "getFavourites error: ${response.code()}")
            completeOnError(response.code())
            false
        }
    }

    private fun ArrayList<FavouriteMovie>.contains(movieId: String): Boolean {
        for (movie in this) {
            if (movie.id == movieId) {
                return true
            }
        }
        return false
    }
}