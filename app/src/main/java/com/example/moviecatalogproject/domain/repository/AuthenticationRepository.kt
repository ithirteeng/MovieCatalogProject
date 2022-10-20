package com.example.moviecatalogproject.domain.repository

interface AuthenticationRepository {

    suspend fun checkTokenExpiration()
}