package com.example.moviecatalogproject.data.service

import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.common.model.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationApiService {
    @POST("api/account/register")
    suspend fun postRegistrationData(@Body registrationData: RegistrationData): Response<Token>

    @POST("api/account/login")
    suspend fun postAuthorizationData(@Body authorizationData: AuthorizationData): Response<Token>

    @GET("api/account/profile")
    suspend fun checkTokenByGettingProfileData(@Header("Authorization") token: String): Response<Any>

    @POST("api/account/logout")
    suspend fun postLogoutData(@Header("Authorization") token: String)
}