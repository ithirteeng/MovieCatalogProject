package com.example.moviecatalogproject.domain.entrance.usecase

import com.example.moviecatalogproject.domain.entrance.validator.PasswordValidator

class ValidatePasswordUseCase(private val passwordValidator: PasswordValidator) {
    fun execute(string: String): Int {
        return passwordValidator.validateTextField(string)
    }
}