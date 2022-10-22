package com.example.moviecatalogproject.domain.main.model

import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.TokenRepository

class GetTokenUseCase(private val tokenRepository: TokenRepository) {
    fun execute(): Token {
        return tokenRepository.getTokenFromLocalStorage()
    }
}