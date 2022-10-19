package com.example.moviecatalogproject.domain.sign_in.usecase

import com.example.moviecatalogproject.domain.sign_in.validator.DateValidator

class ValidateDateUseCase(private val dateValidator: DateValidator) {
    fun execute(string: String): Int {
        return dateValidator.validateTextField(string)
    }
}