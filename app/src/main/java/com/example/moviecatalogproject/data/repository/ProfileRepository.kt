package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.common.utils.getAuthenticationToken
import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import retrofit2.Response

class ProfileRepository {
    suspend fun getProfileData(token: Token): Response<Profile> {
        return NetworkService.profileApiService.getProfileData(getAuthenticationToken(token.token))
    }

    suspend fun putProfileData(token: Token, profile: Profile): Response<Unit> {
        return NetworkService.profileApiService.putProfileData(
            getAuthenticationToken(token.token),
            profile
        )
    }

}