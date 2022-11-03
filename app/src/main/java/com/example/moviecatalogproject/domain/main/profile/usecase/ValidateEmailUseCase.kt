package com.example.moviecatalogproject.domain.main.profile.usecase

import com.example.moviecatalogproject.domain.common.validator.EmailValidator


class ValidateEmailUseCase {

    private val emailValidator: EmailValidator = EmailValidator()

    fun execute(string: String): Int {
        return emailValidator.validateTextField(string)
    }
}