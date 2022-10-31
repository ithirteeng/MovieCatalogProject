package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.model.Token
import retrofit2.Response

class ProfileRepository {
    suspend fun checkAvatarLinkAccessibility(avatarLink: String): Response<Any> {
        return NetworkService.profileApiService.checkAvatarLinkAccessibility(avatarLink)
    }

    suspend fun getProfileData(token: Token): Response<Profile> {
        return NetworkService.profileApiService.getProfileData(token.token)
    }

    suspend fun putProfileData(token: Token, profile: Profile): Response<Any> {
        return NetworkService.profileApiService.putProfileData(token.token, profile)
    }

}