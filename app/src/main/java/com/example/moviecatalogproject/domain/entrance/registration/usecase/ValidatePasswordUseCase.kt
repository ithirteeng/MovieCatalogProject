package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.common.validator.PasswordValidator

class ValidatePasswordUseCase(private val passwordValidator: PasswordValidator) {
    fun execute(string: String): Int {
        return passwordValidator.validateTextField(string)
    }
}