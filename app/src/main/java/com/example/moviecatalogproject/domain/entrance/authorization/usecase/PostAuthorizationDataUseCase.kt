package com.example.moviecatalogproject.domain.entrance.authorization.usecase

import android.util.Log
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.AuthenticationRepository

class PostAuthorizationDataUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend fun execute(authorizationData: AuthorizationData): Token? {
        val response = authenticationRepository.postAuthorizationData(authorizationData)
        return if (response.isSuccessful) {
            Log.d("AUTH", "authorization: " + response.code().toString())
            response.body()
        } else {
            Log.d("AUTH", "authorization: " + response.code().toString())
            null
        }

    }
}