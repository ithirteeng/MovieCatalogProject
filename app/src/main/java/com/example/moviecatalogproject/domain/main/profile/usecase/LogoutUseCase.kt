package com.example.moviecatalogproject.domain.main.profile.usecase

import android.content.Context
import com.example.moviecatalogproject.data.repository.AuthenticationRepository
import com.example.moviecatalogproject.data.repository.TokenRepository
import com.example.moviecatalogproject.domain.common.model.Token

class LogoutUseCase(private val context: Context) {
    private val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    private val tokenRepository by lazy {
        TokenRepository(context)
    }

    suspend fun execute(token: Token, logoutFunction: () -> Unit): Result<Boolean> {
        return try {
            authenticationRepository.postLogoutData(token)
            tokenRepository.deleteTokenFromLocalStorage()
            logoutFunction()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}