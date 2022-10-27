package com.example.moviecatalogproject.presentation.entrance.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.authorization.usecase.PostAuthorizationDataUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.SaveTokenUseCase
import com.example.moviecatalogproject.domain.model.Token
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val postAuthorizationDataUseCase by lazy {
        PostAuthorizationDataUseCase()
    }

    private val saveTokenUseCase by lazy {
        SaveTokenUseCase(application.applicationContext)
    }

    private val tokenLiveData = MutableLiveData<Token?>()

    fun postAuthorizationData(
        authorizationData: AuthorizationData,
        completeOnError: (errorId: Int) -> Unit
    ) {
        viewModelScope.launch {
            tokenLiveData.value = postAuthorizationDataUseCase.execute(
                authorizationData,
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

}