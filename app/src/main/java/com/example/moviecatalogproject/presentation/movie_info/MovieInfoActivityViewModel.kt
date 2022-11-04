package com.example.moviecatalogproject.presentation.movie_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.profile.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.movie_info.usecase.CheckIfMovieIsFavouriteUseCase
import kotlinx.coroutines.launch

class MovieInfoActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val getTokenFromLocalStorageUseCase =
        GetTokenFromLocalStorageUseCase(application.applicationContext)

    private val token by lazy {
        getTokenFromLocalStorageUseCase.execute()
    }

    private val bearerToken by lazy {
        Token("Bearer ${token.token}")
    }

    private val checkIfMovieIsFavouriteUseCase = CheckIfMovieIsFavouriteUseCase()
    private val checkIsFavouriteResultLiveData = MutableLiveData<Boolean>()

    fun checkIsMovieFavourite(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            checkIsFavouriteResultLiveData.value =
                checkIfMovieIsFavouriteUseCase.execute(bearerToken, movieId, completeOnError)
        }
    }

    fun getCheckingIsMovieFavouriteResultLiveData(): LiveData<Boolean> {
        return checkIsFavouriteResultLiveData
    }


}