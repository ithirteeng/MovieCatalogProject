package com.example.moviecatalogproject.domain.entrance.usecase

import com.example.moviecatalogproject.domain.entrance.validator.NameValidator

class ValidateNameUseCase(private val nameValidator: NameValidator) {
    fun execute(string: String): Int {
        return nameValidator.validateTextField(string)
    }
}