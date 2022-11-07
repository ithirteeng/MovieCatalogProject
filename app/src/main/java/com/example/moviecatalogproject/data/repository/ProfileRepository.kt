package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.common.model.Token
import retrofit2.Response

class ProfileRepository {
    suspend fun getProfileData(token: Token): Response<Profile> {
        return NetworkService.profileApiService.getProfileData(token.token)
    }

    suspend fun putProfileData(token: Token, profile: Profile): Response<Unit> {
        return NetworkService.profileApiService.putProfileData(token.token, profile)
    }

}