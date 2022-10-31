package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.validator.Validator

class ValidateTextFieldsUseCase() {
    fun execute(correctValidator: Validator, data: String): Int {
        return correctValidator.validateTextField(data)
    }

}