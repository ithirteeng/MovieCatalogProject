package com.example.moviecatalogproject.domain.common.model

import com.example.moviecatalogproject.R

class ErrorType {
    companion object {
        const val OK = R.string.all_is_ok
        const val DATE_ERROR = R.string.date_error
        const val PASSWORD_SIZE_ERROR = R.string.password_size_error
        const val PASSWORDS_EQUALITY_ERROR = R.string.passwords_equality_error
        const val EMAIL_ERROR = R.string.email_error
        const val EMPTINESS_ERROR = R.string.emptiness_error
        const val PICKER_ERROR = R.string.picker_error
    }
}