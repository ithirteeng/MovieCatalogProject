package com.example.moviecatalogproject.domain.entrance.registration.validator

import com.example.moviecatalogproject.domain.entrance.registration.model.ErrorType

class EmailValidator : Validator {
    override fun validateTextField(string: String): Int {
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (!string.contains("@")) {
            ErrorType.EMAIL_ERROR
        } else {
            ErrorType.OK
        }
    }

}