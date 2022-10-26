package com.example.moviecatalogproject.domain.launch.usecase

import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.TokenRepository

class GetTokenFromLocalStorageUseCase(private val tokenRepository: TokenRepository) {
    fun execute(): Token {
        return tokenRepository.getTokenFromLocalStorage()
    }
}