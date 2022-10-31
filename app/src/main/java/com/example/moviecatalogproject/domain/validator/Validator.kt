package com.example.moviecatalogproject.domain.validator

interface Validator {
    fun validateTextField(string: String): Int
}