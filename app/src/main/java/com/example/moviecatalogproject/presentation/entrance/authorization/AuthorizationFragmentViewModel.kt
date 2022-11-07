package com.example.moviecatalogproject.presentation.entrance.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.authorization.usecase.PostAuthorizationDataUseCase
import com.example.moviecatalogproject.domain.entrance.authorization.usecase.SaveTokenUseCase
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel(application: Application) : AndroidViewModel(application) {

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
            tokenLiveData.value = postAuthorizationDataUseCase.execute(
                authorizationData, completeOnError
            )
        }
    }

    fun getTokenLiveData(): LiveData<Token?> {
        return tokenLiveData
    }

}