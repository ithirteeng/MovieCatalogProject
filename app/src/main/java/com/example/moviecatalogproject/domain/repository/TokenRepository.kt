package com.example.moviecatalogproject.domain.repository

import com.example.moviecatalogproject.domain.model.Token

interface TokenRepository {
    fun saveTokenToLocalStorage(token: Token)

    fun getTokenFromLocalStorage(): Token

    fun deleteTokenFromLocalStorage()
}