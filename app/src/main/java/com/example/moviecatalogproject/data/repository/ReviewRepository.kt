package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.common.utils.getAuthenticationToken
import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import retrofit2.Response

class ReviewRepository {
    suspend fun addReview(token: Token, movieId: String, reviewShort: ReviewShort): Response<Unit> {
        return NetworkService.reviewApiService.postReview(
            movieId,
            getAuthenticationToken(token.token),
            reviewShort
        )
    }

    suspend fun changeReview(
        token: Token,
        movieId: String,
        reviewId: String,
        reviewShort: ReviewShort
    ): Response<Unit> {
        return NetworkService
            .reviewApiService
            .putReviewEdits(movieId, reviewId, getAuthenticationToken(token.token), reviewShort)
    }

    suspend fun deleteReview(token: Token, movieId: String, reviewId: String): Response<Unit> {
        return NetworkService.reviewApiService.deleteReview(
            movieId,
            reviewId,
            getAuthenticationToken(token.token)
        )
    }
}