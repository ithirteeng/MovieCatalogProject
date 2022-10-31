package com.example.moviecatalogproject.presentation.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalogproject.domain.main.profile.usecase.ValidateDateUseCase
import com.example.moviecatalogproject.domain.main.profile.usecase.ValidateEmailUseCase

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val validateDateUseCase = ValidateDateUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()


    fun getEmailErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateEmailUseCase.execute(string))
    }

    fun getDateErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateDateUseCase.execute(string))
    }
}