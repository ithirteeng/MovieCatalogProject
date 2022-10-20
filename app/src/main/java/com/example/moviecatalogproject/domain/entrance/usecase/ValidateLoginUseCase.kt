package com.example.moviecatalogproject.domain.entrance.usecase

import com.example.moviecatalogproject.domain.entrance.validator.LoginValidator

class ValidateLoginUseCase(private val loginValidator: LoginValidator) {
    fun execute(string: String): Int {
        return loginValidator.validateTextField(string)
    }
}