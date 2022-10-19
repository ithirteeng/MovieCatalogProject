package com.example.moviecatalogproject.domain.sign_in.usecase

import com.example.moviecatalogproject.domain.sign_in.validator.LoginValidator

class ValidateLoginUseCase(private val loginValidator: LoginValidator) {
    fun execute(string: String): Int {
        return loginValidator.validateTextField(string)
    }
}