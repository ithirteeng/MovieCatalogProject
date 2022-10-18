package com.example.moviecatalogproject.domain.sign_in.validator

import com.example.moviecatalogproject.domain.sign_in.model.ErrorType

class PasswordValidator : Validator {
    override fun validateTextField(string: String): Int {
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (string.length < 8) {
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