package com.example.moviecatalogproject.domain.main.profile.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.ProfileRepository
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.common.model.Token

class GetProfileDataUseCase {

    private val profileRepository by lazy {
        ProfileRepository()
    }

    suspend fun execute(token: Token, completeOnFailure: () -> Unit): Profile? {
        val response = profileRepository.getProfileData(token)
        return if (response.isSuccessful) {
            Log.d("PROFILE", "get success")
            response.body()
        } else {
            Log.d("PROFILE", "get failure: ${response.code()}")
            completeOnFailure()
            null
        }
    }
}