package com.example.moviecatalogproject.data.storage

import com.example.moviecatalogproject.domain.common.model.Token

interface TokenStorage {
    companion object {
        const val TOKEN_KEY = "userToken"
        const val EMPTINESS_KEY = "empty"
    }

    fun saveToken(token: Token)

    fun getToken(): Token

    fun deleteToken()
}