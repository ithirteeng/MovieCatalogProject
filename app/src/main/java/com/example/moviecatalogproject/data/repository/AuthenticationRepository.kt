package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.common.model.Token
import retrofit2.Response

class AuthenticationRepository {

    suspend fun postAuthorizationData(authorizationData: AuthorizationData): Response<Token> {
        return NetworkService.authenticationApiService.postAuthorizationData(authorizationData)
    }

    suspend fun postRegistrationData(registrationData: RegistrationData): Response<Token> {
        return NetworkService.authenticationApiService.postRegistrationData(registrationData)
    }

    suspend fun postLogoutData(token: Token) {
        NetworkService.authenticationApiService.postLogoutData(token.token)
    }

}