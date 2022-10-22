package com.example.moviecatalogproject.domain.entrance.authorization.usecase

import android.util.Log
import com.example.moviecatalogproject.domain.ResponseHelper
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.AuthenticationRepository

class PostAuthorizationDataUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend fun execute(
        authorizationData: AuthorizationData,
        complete: (stringId: Int) -> Unit
    ): Token? {
        val response = authenticationRepository.postAuthorizationData(authorizationData)
        return if (response.isSuccessful) {
            Log.d("AUTH", "authorization success: " + response.code().toString())
            response.body()
        } else {
            Log.d("AUTH", "authorization error: " + response.code().toString())
            complete(ResponseHelper.errorCodeToStringId(response.code()))
            null
        }

    }
}