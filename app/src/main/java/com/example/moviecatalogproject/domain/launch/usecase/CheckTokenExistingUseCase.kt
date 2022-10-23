package com.example.moviecatalogproject.domain.launch.usecase

import com.example.moviecatalogproject.domain.repository.TokenRepository

class CheckTokenExistingUseCase(private val tokenRepository: TokenRepository) {
    fun execute(): Boolean {
        return tokenRepository.checkTokenExisting()
    }
}