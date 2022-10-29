package com.example.moviecatalogproject.domain.main.usecase

import android.content.Context
import com.example.moviecatalogproject.data.repository.TokenRepository
import com.example.moviecatalogproject.domain.model.Token

class GetTokenUseCase(private val context: Context) {

    private val tokenRepository by lazy {
        TokenRepository(context)
    }

    fun execute(): Token {
        return tokenRepository.getTokenFromLocalStorage()
    }
}