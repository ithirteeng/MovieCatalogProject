package com.example.moviecatalogproject.domain.main.profile.usecase

import com.example.moviecatalogproject.domain.common.model.ErrorType


class ValidateEmailUseCase {
    fun execute(string: String): Int {
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (!string.contains("@")) {
            ErrorType.EMAIL_ERROR
        } else {
            ErrorType.OK
        }
    }
}