package com.example.moviecatalogproject.domain

import com.example.moviecatalogproject.R

object ResponseOnFailureHelper {
    fun registrationOnFailure(code: Int): Int {
        return when (code) {
            400 -> return R.string.error_registration_400
            401 -> return R.string.error_401
            else -> R.string.all_is_ok
        }
    }

    fun authorizationOnFailure(code: Int): Int {
        return when (code) {
            400 -> return R.string.error_authorization_400
            401 -> return R.string.error_401
            else -> R.string.all_is_ok
        }
    }
}