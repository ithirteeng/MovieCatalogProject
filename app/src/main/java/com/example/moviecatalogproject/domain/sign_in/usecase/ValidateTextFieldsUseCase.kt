package com.example.moviecatalogproject.domain.sign_in.usecase

import com.example.moviecatalogproject.domain.sign_in.validator.Validator

class ValidateTextFieldsUseCase() {
    fun execute(correctValidator: Validator, data: String): Int {
        return correctValidator.validateTextField(data)
    }

}