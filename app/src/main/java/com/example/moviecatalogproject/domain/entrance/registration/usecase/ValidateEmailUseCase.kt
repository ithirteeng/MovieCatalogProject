package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.validator.EmailValidator

class ValidateEmailUseCase(private val emailValidator: EmailValidator) {
    fun execute(string: String): Int {
        return emailValidator.validateTextField(string)
    }
}