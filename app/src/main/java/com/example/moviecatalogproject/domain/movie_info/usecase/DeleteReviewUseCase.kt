package com.example.moviecatalogproject.domain.movie_info.usecase

import com.example.moviecatalogproject.data.repository.ReviewRepository
import com.example.moviecatalogproject.domain.common.model.Token

class DeleteReviewUseCase {
    private val reviewRepository by lazy {
        ReviewRepository()
    }

    suspend fun execute(token: Token, movieId: String, reviewId: String): Result<Boolean> {
        return try {
            reviewRepository.deleteReview(token, movieId, reviewId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}