package com.example.moviecatalogproject.domain.main.profile.usecase

import com.example.moviecatalogproject.data.repository.ProfileRepository

class CheckAvatarLinkUseCase {

    private val profileRepository by lazy {
        ProfileRepository()
    }

    suspend fun execute(avatarLink: String): Boolean {
        val response = profileRepository.checkAvatarLinkAccessibility(avatarLink)
        return response.isSuccessful
    }
}