package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.model.FavouritesResponse
import com.example.moviecatalogproject.data.model.GalleryResponse
import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.example.moviecatalogproject.domain.model.Token
import retrofit2.Response

class MoviesRepository {

    suspend fun getFavouritesList(token: Token): Response<FavouritesResponse> {
        return NetworkService.moviesApiService.getFavouritesList(token.token)
    }

    suspend fun postFavouritesAddedData(movieId: String, token: Token): Response<Unit> {
        return NetworkService.moviesApiService.postFavouritesAddedData(movieId, token.token)
    }

    suspend fun deleteFavourites(movieId: String, token: Token): Response<Unit> {
        return NetworkService.moviesApiService.deleteFavouritesData(movieId, token.token)
    }

    suspend fun getMoviesList(moviesPage: Int, token: Token): Response<GalleryResponse> {
        return NetworkService.moviesApiService.getMoviesDataList(moviesPage, token.token)
    }

    suspend fun getMovieDetails(movieId: String, token: Token): Response<Movie> {
        return NetworkService.moviesApiService.getMovieDetails(movieId, token.token)
    }

}