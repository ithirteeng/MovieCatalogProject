package com.example.moviecatalogproject.domain.common.token.usecase

import android.content.Context
import com.example.moviecatalogproject.data.repository.TokenRepository
import com.example.moviecatalogproject.domain.common.token.model.Token

class GetTokenFromLocalStorageUseCase(private val context: Context) {

    private val tokenRepositoryImpl by lazy {
        TokenRepository(context)
    }

    fun execute(): Token {
        return tokenRepositoryImpl.getTokenFromLocalStorage()
    }
}