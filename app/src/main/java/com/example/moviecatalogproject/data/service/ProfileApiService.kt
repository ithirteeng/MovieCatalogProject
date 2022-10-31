package com.example.moviecatalogproject.data.service

import com.example.moviecatalogproject.domain.main.profile.model.Profile
import retrofit2.Response
import retrofit2.http.*

interface ProfileApiService {
    @GET
    suspend fun checkAvatarLinkAccessibility(@Url avatarLink: String): Response<Unit>

    @GET("api/account/profile")
    suspend fun getProfileData(@Header("Authorization") token: String): Response<Profile>

    @PUT("api/account/profile")
    suspend fun putProfileData(
        @Header("Authorization") token: String,
        @Body profile: Profile
    ): Response<Unit>

}