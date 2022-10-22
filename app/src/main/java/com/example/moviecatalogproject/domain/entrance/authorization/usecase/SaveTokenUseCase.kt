package com.example.moviecatalogproject.domain.entrance.authorization.usecase

import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.TokenRepository

class SaveTokenUseCase(private val tokenRepository: TokenRepository) {
    fun execute(token: Token) {
        tokenRepository.saveTokenToLocalStorage(token)
    }
}