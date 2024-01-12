package com.example.moviecatalogproject.data.common.utils

private const val BEARER_AUTHORIZATION = "Bearer"

fun getAuthenticationToken(token: String): String {
    return "$BEARER_AUTHORIZATION $token"
}