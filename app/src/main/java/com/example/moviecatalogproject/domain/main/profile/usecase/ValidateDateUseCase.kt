package com.example.moviecatalogproject.domain.main.profile.usecase

import com.example.moviecatalogproject.domain.common.validator.DateValidator


class ValidateDateUseCase {
    private val dateValidator: DateValidator = DateValidator()

    fun execute(string: String): Int {
        return dateValidator.validateTextField(string)
    }
}