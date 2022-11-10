package com.example.moviecatalogproject.domain.entrance.registration.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.AuthenticationRepository
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData

class PostRegistrationDataUseCase {

    private val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    suspend fun execute(
        registrationData: RegistrationData,
        completeOnError: (errorCode: Int) -> Unit
    ): Result<Token?> {
        return try {
            val response = authenticationRepository.postRegistrationData(registrationData)
            if (response.isSuccessful) {
                Log.d("AUTH", "registration success: ${response.code()}")
                Result.success(response.body())
            } else {
                Log.d("AUTH", "registration error: ${response.code()}")
                completeOnError(response.code())
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}