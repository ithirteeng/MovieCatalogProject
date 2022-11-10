package com.example.moviecatalogproject.domain.movie_info.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.ProfileRepository
import com.example.moviecatalogproject.domain.common.model.Token

class GetUserIdUseCase {

    private val profileRepository by lazy {
        ProfileRepository()
    }

    suspend fun execute(
        token: Token,
        completeOnFailure: (errorCode: Int) -> Unit
    ): Result<String?> {
        return try {
            val response = profileRepository.getProfileData(token)
            if (response.isSuccessful) {
                Log.d("REVIEW", "get id success")
                Result.success(response.body()?.id)
            } else {
                Log.d("REVIEW", "get id failure: ${response.code()}")
                completeOnFailure(response.code())
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}