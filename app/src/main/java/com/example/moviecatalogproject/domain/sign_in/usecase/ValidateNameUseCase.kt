package com.example.moviecatalogproject.domain.sign_in.usecase

import com.example.moviecatalogproject.domain.sign_in.validator.NameValidator

class ValidateNameUseCase(private val nameValidator: NameValidator) {
    fun execute(string: String): Int {
        return nameValidator.validateTextField(string)
    }
}