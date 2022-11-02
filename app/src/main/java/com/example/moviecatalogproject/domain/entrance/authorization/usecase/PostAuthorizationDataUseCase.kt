package com.example.moviecatalogproject.domain.entrance.authorization.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.AuthenticationRepository
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.model.Token

class PostAuthorizationDataUseCase {

    private val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    suspend fun execute(
        authorizationData: AuthorizationData,
        completeOnError: (errorCode: Int) -> Unit
    ): Token? {
        val response = authenticationRepository.postAuthorizationData(authorizationData)
        return if (response.isSuccessful) {
            Log.d("AUTH", "authorization success: " + response.code().toString())
            response.body()
        } else {
            Log.d("AUTH", "authorization error: " + response.code().toString())
            completeOnError(response.code())
            null
        }

    }
}