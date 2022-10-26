package com.example.moviecatalogproject.domain.launch.usecase

import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.TokenRepository

class CheckTokenExpirationUseCase(private val tokenRepository: TokenRepository) {
    suspend fun execute(token: Token): Boolean {
        return tokenRepository.checkTokenExpiration(token)
    }
}