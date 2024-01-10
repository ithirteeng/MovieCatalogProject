package com.example.moviecatalogproject.presentation.entrance.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.token.model.Token
import com.example.moviecatalogproject.domain.common.token.usecase.SaveTokenUseCase
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.authorization.usecase.PostAuthorizationDataUseCase
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel(
    application: Application,
    private val onInternetConnectionFailure: () -> Unit
) : AndroidViewModel(application) {

    private val saveTokenUseCase = SaveTokenUseCase(application.applicationContext)
    fun saveTokenToLocalStorage(token: Token) {
        saveTokenUseCase.execute(token)
    }

    private val postAuthorizationDataUseCase = PostAuthorizationDataUseCase()
    private val tokenLiveData = MutableLiveData<Token?>()
    fun postAuthorizationData(
        authorizationData: AuthorizationData, completeOnError: (errorCode: Int) -> Unit
    ) {
        viewModelScope.launch {
            postAuthorizationDataUseCase.execute(authorizationData, completeOnError).onSuccess {
                tokenLiveData.value = it
            }.onFailure {
                onInternetConnectionFailure()
            }
        }
    }

    fun getTokenLiveData(): LiveData<Token?> {
        return tokenLiveData
    }

}