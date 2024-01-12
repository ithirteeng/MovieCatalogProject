package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.common.utils.getAuthenticationToken
import com.example.moviecatalogproject.data.model.FavouritesResponse
import com.example.moviecatalogproject.data.model.GalleryResponse
import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.MovieDetails
import retrofit2.Response

class MoviesRepository {
    suspend fun getFavouritesList(token: Token): Response<FavouritesResponse> {
        return NetworkService.moviesApiService.getFavouritesList(getAuthenticationToken(token.token))
    }

    suspend fun addToFavourites(movieId: String, token: Token): Response<Unit> {
        return NetworkService.moviesApiService.postFavouritesAddedData(
            movieId,
            getAuthenticationToken(token.token)
        )
    }

    suspend fun deleteFromFavourites(movieId: String, token: Token): Response<Unit> {
        return NetworkService.moviesApiService.deleteFavouritesData(
            movieId,
            getAuthenticationToken(token.token)
        )
    }

    suspend fun getMoviesList(moviesPage: Int, token: Token): Response<GalleryResponse> {
        return NetworkService.moviesApiService.getMoviesDataList(
            moviesPage,
            getAuthenticationToken(token.token)
        )
    }

    suspend fun getMovieDetails(movieId: String, token: Token): Response<MovieDetails> {
        return NetworkService.moviesApiService.getMovieDetails(
            movieId,
            getAuthenticationToken(token.token)
        )
    }

}