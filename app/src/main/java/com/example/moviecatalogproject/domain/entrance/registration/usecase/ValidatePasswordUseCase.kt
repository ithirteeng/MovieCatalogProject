package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType

class ValidatePasswordUseCase {
    fun execute(string: String): Int {
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (string.length < 6) {
            ErrorType.PASSWORD_SIZE_ERROR
        } else {
            if (string.contains("\n")) {
                checkPasswordsOnEquality(string)
            } else {
                ErrorType.OK
            }
        }
    }

    private fun checkPasswordsOnEquality(string: String): Int {
        val passwords = string.split("\n")
        return if (passwords.first() == passwords.last()) {
            ErrorType.OK
        } else {
            ErrorType.PASSWORDS_EQUALITY_ERROR
        }
    }
}