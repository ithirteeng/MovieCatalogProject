package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import retrofit2.Response

class ReviewRepository {
    suspend fun addReview(token: Token, movieId: String, reviewShort: ReviewShort): Response<Unit> {
        return NetworkService.reviewApiService.postReview(movieId, token.token, reviewShort)
    }

    suspend fun changeReview(
        token: Token,
        movieId: String,
        reviewId: String,
        reviewShort: ReviewShort
    ): Response<Unit> {
        return NetworkService
            .reviewApiService
            .putReviewEdits(movieId, reviewId, token.token, reviewShort)
    }

    suspend fun deleteReview(token: Token, movieId: String, reviewId: String): Response<Unit> {
        return NetworkService.reviewApiService.deleteReview(movieId, reviewId, token.token)
    }
}