package com.example.moviecatalogproject.domain.launch.usecase

import android.content.Context
import com.example.moviecatalogproject.data.repository.TokenRepository
import com.example.moviecatalogproject.domain.model.Token

class CheckTokenExpirationUseCase(private val context: Context) {

    private val tokenRepositoryImpl by lazy {
        TokenRepository(context)
    }

    suspend fun execute(token: Token): Boolean {
        return tokenRepositoryImpl.checkTokenExpiration(token)
    }
}