package com.example.moviecatalogproject.domain.entrance.usecase

import com.example.moviecatalogproject.domain.entrance.validator.Validator

class ValidateTextFieldsUseCase() {
    fun execute(correctValidator: Validator, data: String): Int {
        return correctValidator.validateTextField(data)
    }

}