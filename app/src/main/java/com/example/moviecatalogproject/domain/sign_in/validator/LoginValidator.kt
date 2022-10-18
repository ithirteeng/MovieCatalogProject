package com.example.moviecatalogproject.domain.sign_in.validator

import com.example.moviecatalogproject.domain.sign_in.model.ErrorType

class LoginValidator : Validator {
    override fun validateTextField(string: String): Int {
        return if (string.isNotEmpty()) {
            ErrorType.OK
        } else {
            ErrorType.EMPTINESS_ERROR
        }
    }
}