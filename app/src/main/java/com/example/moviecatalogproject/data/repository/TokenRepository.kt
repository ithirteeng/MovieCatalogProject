package com.example.moviecatalogproject.data.repository

import android.content.Context
import com.example.moviecatalogproject.data.service.NetworkService
import com.example.moviecatalogproject.data.storage.SharedPreferencesStorage
import com.example.moviecatalogproject.domain.common.model.Token

class TokenRepository(context: Context) {

    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    fun saveTokenToLocalStorage(token: Token) {
        sharedPreferencesStorage.saveToken(token)
    }

    fun getTokenFromLocalStorage(): Token {
        return sharedPreferencesStorage.getToken()
    }

    fun deleteTokenFromLocalStorage() {
        sharedPreferencesStorage.deleteToken()
    }

    suspend fun checkTokenExpiration(token: Token): Boolean {
        val response =
            NetworkService.authenticationApiService.checkTokenByGettingProfileData(token.token)
        return response.code() != 401
    }

}