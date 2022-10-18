package com.example.moviecatalogproject.presentation.sign_in

import androidx.lifecycle.ViewModel
import com.example.moviecatalogproject.domain.sign_in.usecase.ValidateTextFieldsUseCase
import com.example.moviecatalogproject.domain.sign_in.validator.*

class SignInViewActivityModel : ViewModel() {

    companion object {
        const val LOGIN = "login"
        const val EMAIL = "email"
        const val NAME = "name"
        const val PASSWORD_EQUALITY = "password equality"
        const val PASSWORD_SIZE = "password size"
        const val DATE = "date"
        const val MALE = "male"
    }

    private val validateTextFieldsUseCase by lazy {
        ValidateTextFieldsUseCase()
    }

    fun getErrorId(type: String, string: String): Int {
        val validator = setCorrectValidator(type)
        return validateTextFieldsUseCase.execute(validator, string)

    }

    private fun setCorrectValidator(type: String): Validator {
        return when (type) {
            NAME -> NameValidator()
            PASSWORD_SIZE -> PasswordValidator()
            PASSWORD_EQUALITY -> PasswordValidator()
            DATE -> DateValidator()
            LOGIN -> LoginValidator()
            else -> EmailValidator()
        }
    }

}