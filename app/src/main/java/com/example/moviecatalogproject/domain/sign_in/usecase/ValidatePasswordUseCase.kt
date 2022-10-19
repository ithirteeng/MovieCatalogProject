package com.example.moviecatalogproject.domain.sign_in.usecase

import com.example.moviecatalogproject.domain.sign_in.validator.PasswordValidator

class ValidatePasswordUseCase(private val passwordValidator: PasswordValidator) {
    fun execute(string: String): Int {
        return passwordValidator.validateTextField(string)
    }
}