package com.example.moviecatalogproject.presentation.entrance.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogproject.domain.entrance.registration.usecase.*
import com.example.moviecatalogproject.domain.entrance.registration.validator.*

class RegistrationFragmentViewModel: ViewModel() {
    private val validatePasswordUseCase = ValidatePasswordUseCase(PasswordValidator())
    private val validateEmailUseCase = ValidateEmailUseCase(EmailValidator())
    private val validateLoginUseCase = ValidateLoginUseCase(LoginValidator())
    private val validateNameUseCase = ValidateNameUseCase(NameValidator())
    private val validateDateUseCase = ValidateDateUseCase(DateValidator())


    fun getPasswordErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validatePasswordUseCase.execute(string))
    }

    fun getEmailErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateEmailUseCase.execute(string))
    }

    fun getNameErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateNameUseCase.execute(string))
    }

    fun getLoginErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateLoginUseCase.execute(string))
    }

    fun getDateErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateDateUseCase.execute(string))
    }
}