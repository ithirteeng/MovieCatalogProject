package com.example.moviecatalogproject.domain.main.movie.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.MoviesRepository
import com.example.moviecatalogproject.domain.main.movie.helper.MovieMapper
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.presentation.main.model.GalleryMovie

class GetMoviesListUseCase {

    private val moviesRepository by lazy {
        MoviesRepository()
    }

    suspend fun execute(
        moviesPage: Int,
        token: Token,
        completeOnError: (errorCode: Int) -> Unit
    ): ArrayList<GalleryMovie>? {
        val response = moviesRepository.getMoviesList(moviesPage, token)
        return if (response.isSuccessful) {
            Log.d("MOVIEREP", "getList success")
            MovieMapper.galleryResponseToGalleryMovieList(response.body()!!)
        } else {
            Log.d("MOVIEREP", "getList error: ${response.code()}")
            completeOnError(response.code())
            null
        }
    }

}