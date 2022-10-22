package com.example.moviecatalogproject.domain.entrance.registration.validator

interface Validator {
    fun validateTextField(string: String): Int
}