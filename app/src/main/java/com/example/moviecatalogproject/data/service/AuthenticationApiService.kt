package com.example.moviecatalogproject.data.service

import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.model.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApiService {
    @POST("api/account/register")
    suspend fun postRegistrationData(@Body registrationData: RegistrationData): Response<Token>

    @POST("api/account/login")
    suspend fun postAuthorizationData(@Body authorizationData: AuthorizationData): Response<Token>


}