package com.example.moviecatalogproject.presentation.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.common.token.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.common.validation.usecase.ValidateDateUseCase
import com.example.moviecatalogproject.domain.common.validation.usecase.ValidateEmailUseCase
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.main.profile.usecase.GetProfileDataUseCase
import com.example.moviecatalogproject.domain.main.profile.usecase.LogoutUseCase
import com.example.moviecatalogproject.domain.main.profile.usecase.PutProfileDataUseCase
import com.example.moviecatalogproject.presentation.common.SingleEventLiveData
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    application: Application,
    private val onInternetConnectionFailure: () -> Unit,
    private val onButtonInternetConnectionFailure: () -> Unit
) : AndroidViewModel(application) {

    private var canOnFailureBeCalled = true

    fun setCanOnFailureBeCalled(state: Boolean) {
        canOnFailureBeCalled = state
    }

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
            getProfileDataUseCase.execute(bearerToken, completeOnError).onSuccess {
                canOnFailureBeCalled = true
                profileLiveData.value = it
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
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
            putProfileDataUseCase.execute(bearerToken, profile, completeOnError).onSuccess {
                canOnFailureBeCalled = true
                onSavingProfileChangesLiveData.value = it
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onButtonInternetConnectionFailure()
                }
            }


        }
    }

    fun getOnSavingProfileChangesLiveData(): LiveData<Boolean> {
        return onSavingProfileChangesLiveData
    }


    private val logoutUseCase = LogoutUseCase(application.applicationContext)
    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase.execute(bearerToken, onLogout).onSuccess {
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onButtonInternetConnectionFailure()
                }
            }
        }
    }
}

