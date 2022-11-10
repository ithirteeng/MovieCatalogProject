package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.movie.helper.MovieMapper
import com.example.moviecatalogproject.presentation.main.movie.model.FavouriteMovie

class GetFavouritesListUseCase {
    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        token: Token,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<ArrayList<FavouriteMovie>?> {
        return try {
            val response = moviesRepository.getFavouritesList(token)
            if (response.isSuccessful) {
                Log.d("MOVIEREP", "getFavourites success")
                Result.success(MovieMapper.favouritesResponseToFavouritesList(response.body()!!))
            } else {
                Log.d("MOVIEREP", "getFavourites error: ${response.code()}")
                completeOnError(response.code())
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}