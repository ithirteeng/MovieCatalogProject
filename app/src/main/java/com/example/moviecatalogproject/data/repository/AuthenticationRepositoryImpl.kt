package com.example.moviecatalogproject.data.repository

import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.AuthenticationRepository
import retrofit2.Response

class AuthenticationRepositoryImpl : AuthenticationRepository {

    override suspend fun postAuthorizationData(authorizationData: AuthorizationData): Response<Token> {
        return NetworkService.authenticationApiService.postAuthorizationData(authorizationData)
    }

    override suspend fun postRegistrationData(registrationData: RegistrationData): Response<Token> {
        return NetworkService.authenticationApiService.postRegistrationData(registrationData)
    }

    override suspend fun postLogoutData() {
        TODO("Not yet implemented")
    }

}