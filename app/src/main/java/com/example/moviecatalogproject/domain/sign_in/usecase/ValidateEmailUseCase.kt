package com.example.moviecatalogproject.domain.sign_in.usecase

import com.example.moviecatalogproject.domain.sign_in.validator.EmailValidator

class ValidateEmailUseCase(private val emailValidator: EmailValidator) {
    fun execute(string: String): Int {
        return emailValidator.validateTextField(string)
    }
}