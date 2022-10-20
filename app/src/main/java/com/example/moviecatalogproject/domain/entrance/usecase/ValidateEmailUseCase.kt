package com.example.moviecatalogproject.domain.entrance.usecase

import com.example.moviecatalogproject.domain.entrance.validator.EmailValidator

class ValidateEmailUseCase(private val emailValidator: EmailValidator) {
    fun execute(string: String): Int {
        return emailValidator.validateTextField(string)
    }
}