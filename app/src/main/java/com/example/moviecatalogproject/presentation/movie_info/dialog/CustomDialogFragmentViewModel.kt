package com.example.moviecatalogproject.presentation.movie_info.dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.movie.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import com.example.moviecatalogproject.domain.movie_info.usecase.AddReviewUseCase
import com.example.moviecatalogproject.domain.movie_info.usecase.ChangeReviewUseCase
import com.example.moviecatalogproject.presentation.common.SingleEventLiveData
import kotlinx.coroutines.launch

class CustomDialogFragmentViewModel(
    application: Application,
    private val onInternetConnectionFailure: () -> Unit
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


    private val addReviewUseCase = AddReviewUseCase()
    private val onCompleteAddingLiveData = SingleEventLiveData<Boolean>()
    fun addReview(
        movieId: String,
        reviewShort: ReviewShort,
        completeOnError: (errorCode: Int) -> Unit
    ) {
        viewModelScope.launch {
            addReviewUseCase.execute(bearerToken, movieId, reviewShort, completeOnError).onSuccess {
                onCompleteAddingLiveData.value = it
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

    fun getOnCompleteAddingLiveData(): LiveData<Boolean> {
        return onCompleteAddingLiveData
    }


    private val changeReviewUseCase = ChangeReviewUseCase()
    private val onCompleteChangingLiveData = SingleEventLiveData<Boolean>()
    fun changeReview(
        movieId: String,
        reviewId: String,
        reviewShort: ReviewShort,
        completeOnError: (errorCode: Int) -> Unit
    ) {
        viewModelScope.launch {
            changeReviewUseCase.execute(
                bearerToken,
                movieId,
                reviewId,
                reviewShort,
                completeOnError
            ).onSuccess {
                onCompleteChangingLiveData.value = it
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

    fun getOnCompleteChangingLiveData(): LiveData<Boolean> {
        return onCompleteChangingLiveData
    }

}