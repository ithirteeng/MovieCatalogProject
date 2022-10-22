package com.example.moviecatalogproject.domain.repository

import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.model.Token
import retrofit2.Response

interface AuthenticationRepository {

    suspend fun postAuthorizationData(authorizationData: AuthorizationData): Response<Token>

    suspend fun postRegistrationData(registrationData: RegistrationData): Response<Token>

    suspend fun postLogoutData()
}