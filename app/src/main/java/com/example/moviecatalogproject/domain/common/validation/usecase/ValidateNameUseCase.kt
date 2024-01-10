package com.example.moviecatalogproject.domain.common.validation.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType

class ValidateNameUseCase {
    fun execute(string: String): Int {
        return if (string.isNotEmpty()) {
            ErrorType.OK
        } else {
            ErrorType.EMPTINESS_ERROR
        }
    }
}