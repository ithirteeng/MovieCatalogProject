package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.ReviewRepository
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort

class ChangeReviewUseCase {

    private val reviewRepository by lazy {
        ReviewRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        reviewId: String,
        reviewShort: ReviewShort,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<Boolean> {
        return try {
            val response = reviewRepository.changeReview(token, movieId, reviewId, reviewShort)
            if (!response.isSuccessful) {
                Log.d("REVIEW", "review change error: ${response.code()}")
                completeOnError(response.code())
            }
            Result.success(response.isSuccessful)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}