package com.example.moviecatalogproject.domain.sign_in.validator

interface Validator {
    fun validateTextField(string: String): Int
}