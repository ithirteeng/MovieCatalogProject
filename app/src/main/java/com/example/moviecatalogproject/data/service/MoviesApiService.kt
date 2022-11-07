package com.example.moviecatalogproject.data.service

import com.example.moviecatalogproject.data.model.FavouritesResponse
import com.example.moviecatalogproject.data.model.GalleryResponse
import com.example.moviecatalogproject.domain.movie_info.model.MovieDetails
import retrofit2.Response
import retrofit2.http.*

interface MoviesApiService {

    @GET("api/favorites")
    suspend fun getFavouritesList(@Header("Authorization") token: String): Response<FavouritesResponse>

    @POST("api/favorites/{id}/add")
    suspend fun postFavouritesAddedData(
        @Path("id") movieId: String,
        @Header("Authorization") token: String
    ): Response<Unit>

    @DELETE("api/favorites/{id}/delete")
    suspend fun deleteFavouritesData(
        @Path("id") movieId: String,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("api/movies/{page}")
    suspend fun getMoviesDataList(
        @Path("page") moviesPage: Int,
        @Header("Authorization") token: String
    ): Response<GalleryResponse>

    @GET("api/movies/details/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: String,
        @Header("Authorization") token: String
    ): Response<MovieDetails>

}