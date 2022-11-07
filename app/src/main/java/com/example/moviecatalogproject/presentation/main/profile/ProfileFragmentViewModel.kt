package com.example.moviecatalogproject.presentation.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.main.profile.usecase.*
import com.example.moviecatalogproject.presentation.common.SingleEventLiveData
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val getTokenFromLocalStorageUseCase =
        GetTokenFromLocalStorageUseCase(application.applicationContext)

    private val token by lazy {
        getTokenFromLocalStorageUseCase.execute()
    }

    private val bearerToken by lazy {
        Token("Bearer ${token.token}")
    }


    private val validateEmailUseCase = ValidateEmailUseCase()
    fun getEmailErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validateEmailUseCase.execute(string))
    }


    private val validateDateUseCase = ValidateDateUseCase()
    fun getDateErrorLiveData(string: String): LiveData<Int> {
        return MutableLiveData(validateDateUseCase.execute(string))
    }


    private val getProfileDataUseCase = GetProfileDataUseCase()
    private val profileLiveData = SingleEventLiveData<Profile?>()
    fun getProfileData(completeOnError: () -> Unit) {
        viewModelScope.launch {
            profileLiveData.value = getProfileDataUseCase.execute(bearerToken) {
                completeOnError()
            }
        }
    }

    fun getProfileLiveData(): LiveData<Profile?> {
        return profileLiveData
    }


    private val putProfileDataUseCase = PutProfileDataUseCase()
    private val onSavingProfileChangesLiveData = SingleEventLiveData<Boolean>()
    fun putProfileData(profile: Profile, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            onSavingProfileChangesLiveData.value =
                putProfileDataUseCase.execute(bearerToken, profile) {
                    completeOnError(it)
                }
        }
    }

    fun getOnSavingProfileChangesLiveData(): LiveData<Boolean> {
        return onSavingProfileChangesLiveData
    }


    private val logoutUseCase = LogoutUseCase(application.applicationContext)
    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase.execute(bearerToken) {
                onLogout()
            }
        }
    }
}

