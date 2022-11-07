package com.example.moviecatalogproject.domain.main.profile.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.ProfileRepository
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.profile.model.Profile

class PutProfileDataUseCase {

    private val profileRepository by lazy {
        ProfileRepository()
    }

    suspend fun execute(
        token: Token,
        profileData: Profile,
        completeOnFailure: (code: Int) -> Unit
    ): Boolean {
        val response = profileRepository.putProfileData(token, profileData)
        Log.d("PROFILE", "put: ${response.code()}")
        if (!response.isSuccessful) {
            completeOnFailure(response.code())
        }
        return response.isSuccessful
    }
}