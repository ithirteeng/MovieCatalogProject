package com.example.moviecatalogproject.domain.entrance.registration.usecase

import com.example.moviecatalogproject.domain.entrance.registration.validator.DateValidator

class ValidateDateUseCase(private val dateValidator: DateValidator) {
    fun execute(string: String): Int {
        return dateValidator.validateTextField(string)
    }
}