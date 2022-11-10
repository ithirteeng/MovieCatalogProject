package com.example.moviecatalogproject.domain.entrance.authorization.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.AuthenticationRepository
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData

class PostAuthorizationDataUseCase {

    private val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    suspend fun execute(
        authorizationData: AuthorizationData,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<Token?> {
        return try {
            val response = authenticationRepository.postAuthorizationData(authorizationData)
            if (response.isSuccessful) {
                Log.d("AUTH", "authorization success: " + response.code().toString())
                Result.success(response.body())
            } else {
                Log.d("AUTH", "authorization error: " + response.code().toString())
                completeOnError(response.code())
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }


    }
}