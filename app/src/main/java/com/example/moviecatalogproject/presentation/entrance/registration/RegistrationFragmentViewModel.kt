package com.example.moviecatalogproject.presentation.entrance.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.data.repository.AuthenticationRepositoryImpl
import com.example.moviecatalogproject.data.repository.TokenRepositoryImpl
import com.example.moviecatalogproject.domain.entrance.registration.usecase.SaveTokenUseCase
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.entrance.registration.usecase.*
import com.example.moviecatalogproject.domain.entrance.registration.validator.*
import com.example.moviecatalogproject.domain.model.Token
import kotlinx.coroutines.launch

class RegistrationFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val authenticationRepositoryImpl by lazy {
        AuthenticationRepositoryImpl()
    }

    private val tokenRepositoryImpl by lazy {
        TokenRepositoryImpl(application.applicationContext)
    }

    private val saveTokenUseCase by lazy {
        SaveTokenUseCase(tokenRepositoryImpl)
    }

    private val postRegistrationDataUseCase by lazy {
        PostRegistrationDataUseCase(authenticationRepositoryImpl)
    }

    private var tokenLiveData = MutableLiveData<Token?>()

    fun postRegistrationData(
        registrationData: RegistrationData, completeOnError: (stringId: Int) -> Unit
    ) {
        viewModelScope.launch {
            tokenLiveData.value = postRegistrationDataUseCase.execute(
                registrationData,
                completeOnError
            )
        }
    }

    fun saveTokenToLocalStorage(token: Token) {
        saveTokenUseCase.execute(token)
    }

    fun getTokenLiveData(): MutableLiveData<Token?> {
        return tokenLiveData
    }

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