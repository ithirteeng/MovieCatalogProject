package com.example.moviecatalogproject.domain.common.validator

import com.example.moviecatalogproject.domain.common.model.ErrorType

class LoginValidator : Validator {
    override fun validateTextField(string: String): Int {
        return if (string.isNotEmpty()) {
            ErrorType.OK
        } else {
            ErrorType.EMPTINESS_ERROR
        }
    }
}