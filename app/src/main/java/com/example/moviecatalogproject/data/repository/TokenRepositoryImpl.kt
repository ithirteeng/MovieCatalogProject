package com.example.moviecatalogproject.data.repository

import android.content.Context
import com.example.moviecatalogproject.data.storage.SharedPreferencesStorage
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.domain.repository.TokenRepository

class TokenRepositoryImpl(context: Context) : TokenRepository {

    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    override fun saveTokenToLocalStorage(token: Token) {
        sharedPreferencesStorage.saveToken(token)
    }

    override fun getTokenFromLocalStorage(): Token {
        return sharedPreferencesStorage.getToken()
    }

    override fun deleteTokenFromLocalStorage() {
        sharedPreferencesStorage.deleteToken()
    }

}