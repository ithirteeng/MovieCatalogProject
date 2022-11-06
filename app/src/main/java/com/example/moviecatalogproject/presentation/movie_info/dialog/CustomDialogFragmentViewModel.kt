package com.example.moviecatalogproject.presentation.movie_info.dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.main.movie.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import com.example.moviecatalogproject.domain.movie_info.usecase.AddReviewUseCase
import com.example.moviecatalogproject.domain.movie_info.usecase.ChangeReviewUseCase
import kotlinx.coroutines.launch

class CustomDialogFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val getTokenFromLocalStorageUseCase =
        GetTokenFromLocalStorageUseCase(application.applicationContext)

    private val token by lazy {
        getTokenFromLocalStorageUseCase.execute()
    }

    private val bearerToken by lazy {
        Token("Bearer ${token.token}")
    }

    private val addReviewUseCase = AddReviewUseCase()
    fun addReview(
        movieId: String,
        reviewShort: ReviewShort,
        completeOnError: (errorCode: Int) -> Unit
    ) {
        viewModelScope.launch {
            addReviewUseCase.execute(bearerToken, movieId, reviewShort, completeOnError)
        }
    }

    private val changeReviewUseCase = ChangeReviewUseCase()

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
            )
        }
    }

}