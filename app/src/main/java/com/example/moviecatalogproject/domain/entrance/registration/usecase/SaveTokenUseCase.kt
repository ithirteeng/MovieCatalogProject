package com.example.moviecatalogproject.domain.entrance.registration.usecase

import android.content.Context
import com.example.moviecatalogproject.data.repository.TokenRepository
import com.example.moviecatalogproject.domain.common.model.Token

class SaveTokenUseCase(private val context: Context) {

    private val tokenRepository by lazy {
        TokenRepository(context)
    }

    fun execute(token: Token) {
        tokenRepository.saveTokenToLocalStorage(token)
    }
}