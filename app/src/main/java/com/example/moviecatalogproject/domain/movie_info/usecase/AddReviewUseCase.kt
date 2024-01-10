package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.ReviewRepository
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort

class AddReviewUseCase {

    private val reviewRepository by lazy {
        ReviewRepository()
    }

    suspend fun execute(
        token: Token,
        movieId: String,
        reviewShort: ReviewShort,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<Boolean> {
        return try {
            val response = reviewRepository.addReview(token, movieId, reviewShort)
            if (!response.isSuccessful) {
                Log.d("REVIEW", "review post error: ${response.code()}")
                completeOnError(response.code())
            }
            Result.success(response.isSuccessful)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}