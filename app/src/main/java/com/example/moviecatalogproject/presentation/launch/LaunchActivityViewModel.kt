package com.example.moviecatalogproject.presentation.launch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalogproject.data.repository.TokenRepositoryImpl
import com.example.moviecatalogproject.domain.launch.usecase.CheckTokenExistingUseCase

class LaunchActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenRepositoryImpl by lazy {
        TokenRepositoryImpl(application.applicationContext)
    }

    private val checkTokenExistingUseCase by lazy {
        CheckTokenExistingUseCase(tokenRepositoryImpl)
    }

    private val tokenLiveData = MutableLiveData<Boolean>()

    fun getTokenLiveData(): MutableLiveData<Boolean> {
        return tokenLiveData
    }

    fun checkTokenExisting(): Boolean {
        val result = checkTokenExistingUseCase.execute()
        tokenLiveData.value = result
        return result
    }

}