package com.example.moviecatalogproject.domain.common.validator

interface Validator {
    fun validateTextField(string: String): Int
}