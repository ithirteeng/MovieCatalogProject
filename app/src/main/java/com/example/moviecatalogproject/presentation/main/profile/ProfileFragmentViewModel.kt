package com.example.moviecatalogproject.presentation.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.main.profile.usecase.*
import com.example.moviecatalogproject.domain.model.Token
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val validateDateUseCase = ValidateDateUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()


    fun getEmailErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateEmailUseCase.execute(string))
    }

    fun getDateErrorLiveData(string: String): MutableLiveData<Int> {
        return MutableLiveData(validateDateUseCase.execute(string))
    }


    private val getTokenFromLocalStorageUseCase =
        GetTokenFromLocalStorageUseCase(application.applicationContext)

    private val token by lazy {
        getTokenFromLocalStorageUseCase.execute()
    }

    private val bearerToken by lazy {
        Token("Bearer ${token.token}")
    }


    private val getProfileDataUseCase = GetProfileDataUseCase()
    private val putProfileDataUseCase = PutProfileDataUseCase()
    private val profileLiveData = MutableLiveData<Profile?>()

    fun getProfileData(completeOnError: () -> Unit) {
        viewModelScope.launch {
            profileLiveData.value = getProfileDataUseCase.execute(bearerToken) {
                completeOnError()
            }
        }
    }

    fun getProfileLiveData(): MutableLiveData<Profile?> {
        return profileLiveData
    }

    fun putProfileData(profile: Profile, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            putProfileDataUseCase.execute(bearerToken, profile) {
                completeOnError(it)
            }
        }
    }


    private val checkAvatarLinkUseCase = CheckAvatarLinkUseCase()
    private val avatarLinkAccessibilityLiveData = MutableLiveData<Boolean>()

    fun checkAvatarLinkAccessibility(avatarLink: String) {
        viewModelScope.launch {
            avatarLinkAccessibilityLiveData.value = checkAvatarLinkUseCase.execute(avatarLink)
        }
    }

    fun getAvatarLinkAccessibilityLiveData(): MutableLiveData<Boolean> {
        return avatarLinkAccessibilityLiveData
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

