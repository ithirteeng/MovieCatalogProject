package com.example.moviecatalogproject.presentation.entrance.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.common.token.usecase.SaveTokenUseCase
import com.example.moviecatalogproject.domain.entrance.registration.model.RegistrationData
import com.example.moviecatalogproject.domain.entrance.registration.usecase.PostRegistrationDataUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateDateUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateEmailUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateLoginUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidateNameUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.ValidatePasswordUseCase
import kotlinx.coroutines.launch

class RegistrationFragmentViewModel(
    application: Application,
    private val onInternetConnectionFailure: () -> Unit
) : AndroidViewModel(application) {

    private val postRegistrationDataUseCase = PostRegistrationDataUseCase()
    private var tokenLiveData = MutableLiveData<Token?>()
    fun postRegistrationData(
        registrationData: RegistrationData,
        completeOnError: (errorCode: Int) -> Unit
    ) {
        viewModelScope.launch {
            postRegistrationDataUseCase.execute(registrationData, completeOnError).onSuccess {
                tokenLiveData.value = it
            }.onFailure {
                onInternetConnectionFailure()
            }
        }
    }

    fun getTokenLiveData(): MutableLiveData<Token?> {
        return tokenLiveData
    }


    private val saveTokenUseCase = SaveTokenUseCase(application.applicationContext)
    fun saveTokenToLocalStorage(token: Token) {
        saveTokenUseCase.execute(token)
    }


    private val validatePasswordUseCase = ValidatePasswordUseCase()
    fun getPasswordErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validatePasswordUseCase.execute(string))
    }


    private val validateEmailUseCase = ValidateEmailUseCase()
    fun getEmailErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validateEmailUseCase.execute(string))
    }


    private val validateNameUseCase = ValidateNameUseCase()
    fun getNameErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validateNameUseCase.execute(string))
    }


    private val validateLoginUseCase = ValidateLoginUseCase()
    fun getLoginErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validateLoginUseCase.execute(string))
    }


    private val validateDateUseCase = ValidateDateUseCase()
    fun getDateErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validateDateUseCase.execute(string))
    }
}