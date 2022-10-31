package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.validator.LoginValidator

class ValidateLoginUseCase(private val loginValidator: LoginValidator) {
    fun execute(string: String): Int {
        return loginValidator.validateTextField(string)
    }
}