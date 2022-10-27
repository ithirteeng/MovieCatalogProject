package com.example.moviecatalogproject.presentation.launch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.launch.usecase.CheckTokenExpirationUseCase
import com.example.moviecatalogproject.domain.launch.usecase.GetTokenFromLocalStorageUseCase
import kotlinx.coroutines.launch

class LaunchActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val checkTokenExpirationUseCase by lazy {
        CheckTokenExpirationUseCase(application.applicationContext)
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(application.applicationContext)
    }

    private val tokenExistingLiveData = MutableLiveData<Boolean>()


    fun getTokenExistingLiveData(): MutableLiveData<Boolean> {
        return tokenExistingLiveData
    }

    fun checkTokenExisting() {
        viewModelScope.launch {
            val token = getTokenFromLocalStorageUseCase.execute()
            token.token = "Bearer ${token.token}"
            val result = checkTokenExpirationUseCase.execute(token)
            tokenExistingLiveData.value = result
        }
    }

}