package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.main.movie.helper.MovieMapper
import com.example.moviecatalogproject.presentation.main.movie.model.GalleryMovie

class GetMoviesListUseCase {

    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        moviesPage: Int,
        token: Token,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<ArrayList<GalleryMovie>?> {
        return try {
            val response = moviesRepository.getMoviesList(moviesPage, token)
            return if (response.isSuccessful) {
                Log.d("MOVIEREP", "getList success")
                Result.success(MovieMapper.galleryResponseToGalleryMovieList(response.body()!!))
            } else {
                Log.d("MOVIEREP", "getList error: ${response.code()}")
                completeOnError(response.code())
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}