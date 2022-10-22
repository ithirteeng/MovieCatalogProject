package com.example.moviecatalogproject.presentation.entrance.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.data.repository.AuthenticationRepositoryImpl
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.domain.entrance.authorization.usecase.PostAuthorizationDataUseCase
import com.example.moviecatalogproject.domain.model.Token
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel : ViewModel() {

    private val authenticationRepositoryImpl by lazy {
        AuthenticationRepositoryImpl()
    }

    private val postAuthorizationDataUseCase by lazy {
        PostAuthorizationDataUseCase(authenticationRepositoryImpl)
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

    fun getTokenLiveData(): MutableLiveData<Token?> {
        return tokenLiveData
    }

}