package com.example.moviecatalogproject.presentation.entrance.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.data.repository.AuthenticationRepositoryImpl
import com.example.moviecatalogproject.data.repository.TokenRepositoryImpl
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.authorization.usecase.PostAuthorizationDataUseCase
import com.example.moviecatalogproject.domain.entrance.registration.usecase.SaveTokenUseCase
import com.example.moviecatalogproject.domain.model.Token
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val authenticationRepositoryImpl by lazy {
        AuthenticationRepositoryImpl()
    }

    private val postAuthorizationDataUseCase by lazy {
        PostAuthorizationDataUseCase(authenticationRepositoryImpl)
    }

    private val tokenRepositoryImpl by lazy {
        TokenRepositoryImpl(application.applicationContext)
    }

    private val saveTokenUseCase by lazy {
        SaveTokenUseCase(tokenRepositoryImpl)
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