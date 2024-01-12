package com.example.moviecatalogproject.presentation.movie_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.token.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.movie_info.model.MovieDetails
import com.example.moviecatalogproject.domain.movie_info.usecase.*
import com.example.moviecatalogproject.presentation.common.SingleEventLiveData
import kotlinx.coroutines.launch

class MovieInfoActivityViewModel(
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

    private val checkIfMovieIsFavouriteUseCase = CheckIfMovieIsFavouriteUseCase()
    private val checkIsFavouriteResultLiveData = MutableLiveData<Boolean>()
    fun checkIsMovieFavourite(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            checkIfMovieIsFavouriteUseCase.execute(token, movieId, completeOnError)
                .onSuccess {
                    checkIsFavouriteResultLiveData.value = it
                    canOnFailureBeCalled = true
                }
                .onFailure {
                    if (canOnFailureBeCalled) {
                        canOnFailureBeCalled = false
                        onInternetConnectionFailure()
                    }
                }
        }
    }

    fun getCheckingIsMovieFavouriteResultLiveData(): LiveData<Boolean> {
        return checkIsFavouriteResultLiveData
    }


    private val getMovieDetailsUseCase = GetMovieDetailsUseCase()
    private val movieDetailsLivedata = MutableLiveData<MovieDetails>()
    fun getMovieDetails(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            getMovieDetailsUseCase.execute(token, movieId, completeOnError).onSuccess {
                movieDetailsLivedata.value = it
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

    fun getMovieDetailsLiveData(): LiveData<MovieDetails> {
        return movieDetailsLivedata
    }


    private val deleteFromFavouritesUseCase = DeleteFromFavouritesUseCase()
    fun deleteFromFavourites(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            deleteFromFavouritesUseCase.execute(token, movieId, completeOnError).onSuccess {
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }


    private val addToFavouritesUseCase = AddToFavouritesUseCase()
    fun addToFavourites(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            addToFavouritesUseCase.execute(token, movieId, completeOnError)
        }
    }


    private val getUserIdUseCase = GetUserIdUseCase()
    private val userIdLiveData = SingleEventLiveData<String>()
    fun getUserId(completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            getUserIdUseCase.execute(token, completeOnError).onSuccess {
                userIdLiveData.value = it
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

    fun getUserIdLiveData(): LiveData<String> {
        return userIdLiveData
    }


    private val deleteReviewUseCase = DeleteReviewUseCase()
    fun deleteReview(movieId: String, reviewId: String) {
        viewModelScope.launch {
            deleteReviewUseCase.execute(token, movieId, reviewId).onSuccess {
                canOnFailureBeCalled = true
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

}