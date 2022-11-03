package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.common.validator.NameValidator

class ValidateNameUseCase(private val nameValidator: NameValidator) {
    fun execute(string: String): Int {
        return nameValidator.validateTextField(string)
    }
}