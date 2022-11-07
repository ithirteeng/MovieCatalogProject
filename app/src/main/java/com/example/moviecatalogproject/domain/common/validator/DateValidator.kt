package com.example.moviecatalogproject.domain.common.validator

import com.example.moviecatalogproject.domain.common.model.ErrorType

class DateValidator : Validator {
    override fun validateTextField(string: String): Int {
        val firstRegex =
            Regex("(0[1-9]|[1-2]\\d|3[0-1])\\.(01|03|05|07|08|10|12)\\.((20([01]\\d|2[0-2]))|(19\\d\\d))\\s*")
        val secondRegex =
            Regex("(0[1-9]|[12]\\d|30)\\.(04|06|09|11)\\.((20([01]\\d|2[0-2]))|(19\\d\\d))\\s*")
        val thirdRegex =
            Regex("(0[1-9]|[12]\\d)\\.(02)\\.((20([01]\\d|2[0-2]))|(19\\d\\d))\\s*")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (string.matches(firstRegex) || string.matches(secondRegex) || string.matches(
                thirdRegex
            )
        ) {
            ErrorType.OK
        } else {
            ErrorType.DATE_ERROR
        }
    }
}