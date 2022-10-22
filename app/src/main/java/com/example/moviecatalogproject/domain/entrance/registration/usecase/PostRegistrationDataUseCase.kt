package com.example.moviecatalogproject.domain.entrance.registration.usecase

import android.util.Log
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.AuthenticationRepository

class PostRegistrationDataUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend fun execute(registrationData: RegistrationData): Token? {
        val response = authenticationRepository.postRegistrationData(registrationData)
        return if (response.isSuccessful) {
            Log.d("AUTH", "registration: " + response.code().toString())
            response.body()
        } else {
            Log.d("AUTH", "registration: " + response.code().toString())
            null
        }
    }

}