package com.example.moviecatalogproject.domain.entrance.registration.validator

import com.example.moviecatalogproject.domain.entrance.registration.model.ErrorType

class LoginValidator : Validator {
    override fun validateTextField(string: String): Int {
        return if (string.isNotEmpty()) {
            ErrorType.OK
        } else {
            ErrorType.EMPTINESS_ERROR
        }
    }
}