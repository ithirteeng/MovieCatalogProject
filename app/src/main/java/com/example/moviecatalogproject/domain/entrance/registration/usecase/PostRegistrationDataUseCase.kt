package com.example.moviecatalogproject.domain.entrance.registration.usecase

import android.util.Log
import com.example.moviecatalogproject.data.repository.AuthenticationRepository
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.model.Token

class PostRegistrationDataUseCase {

    private val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    suspend fun execute(
        registrationData: RegistrationData,
        completeOnError: (errorCode: Int) -> Unit
    ): Token? {
        val response = authenticationRepository.postRegistrationData(registrationData)
        return if (response.isSuccessful) {
            Log.d("AUTH", "registration success: ${response.code()}")
            response.body()
        } else {
            Log.d("AUTH", "registration error: ${response.code()}")
            completeOnError(response.code())
            null
        }
    }

}