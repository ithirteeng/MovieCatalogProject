package com.example.moviecatalogproject.domain.main.profile.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.ProfileRepository
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.profile.model.Profile

class GetProfileDataUseCase {

    private val profileRepository by lazy {
        ProfileRepository()
    }

    suspend fun execute(token: Token, completeOnFailure: () -> Unit): Result<Profile?> {
        return try {
            val response = profileRepository.getProfileData(token)
            if (response.isSuccessful) {
                Log.d("PROFILE", "get success")
                Result.success(response.body())
            } else {
                Log.d("PROFILE", "get failure: ${response.code()}")
                completeOnFailure()
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}