package com.example.moviecatalogproject.data.service

import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import retrofit2.Response
import retrofit2.http.*

interface ReviewApiService {
    @POST("api/movie/{movieId}/review/add")
    suspend fun postReview(
        @Path("movieId") movieId: String,
        @Header("Authorization") token: String,
        @Body reviewShort: ReviewShort
    ): Response<Unit>

    @PUT("api/movie/{movieId}/review/{id}/edit")
    suspend fun putReviewEdits(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String,
        @Body reviewShort: ReviewShort
    ): Response<Unit>

    @DELETE("api/movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String
    ): Response<Unit>
}