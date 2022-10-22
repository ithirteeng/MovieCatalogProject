package com.example.moviecatalogproject.domain

import com.example.moviecatalogproject.R

object ResponseHelper {
    fun errorCodeToStringId(code: Int): Int {
        return when (code) {
            400 -> return R.string.error_400
            401 -> return R.string.error_401
            else -> R.string.all_is_ok
        }
    }

}