package com.example.moviecatalogproject.domain.entrance.usecase

import com.example.moviecatalogproject.domain.entrance.validator.DateValidator

class ValidateDateUseCase(private val dateValidator: DateValidator) {
    fun execute(string: String): Int {
        return dateValidator.validateTextField(string)
    }
}