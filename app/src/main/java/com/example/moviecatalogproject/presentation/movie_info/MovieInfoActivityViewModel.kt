package com.example.moviecatalogproject.presentation.movie_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.model.Token
import com.example.moviecatalogproject.domain.movie_info.model.MovieDetails
import com.example.moviecatalogproject.domain.movie_info.usecase.*
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

    private val getMovieDetailsUseCase = GetMovieDetailsUseCase()
    private val movieDetailsLivedata = MutableLiveData<MovieDetails>()

    fun getMovieDetails(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            movieDetailsLivedata.value =
                getMovieDetailsUseCase.execute(bearerToken, movieId, completeOnError)
        }
    }

    fun getMovieDetailsLiveData(): LiveData<MovieDetails> {
        return movieDetailsLivedata
    }

    private val deleteFromFavouritesUseCase = DeleteFromFavouritesUseCase()

    fun deleteFromFavourites(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            deleteFromFavouritesUseCase.execute(bearerToken, movieId, completeOnError)
        }
    }

    private val addToFavouritesUseCase = AddToFavouritesUseCase()

    fun addToFavourites(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            addToFavouritesUseCase.execute(bearerToken, movieId, completeOnError)
        }
    }

    private val getUserIdUseCase = GetUserIdUseCase()
    private val userIdLiveData = MutableLiveData<String>()

    fun getUserId(completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            userIdLiveData.value = getUserIdUseCase.execute(bearerToken, completeOnError)
        }
    }

    fun getUserIdLiveData(): LiveData<String> {
        return userIdLiveData
    }

    private val deleteReviewUseCase = DeleteReviewUseCase()

    fun deleteReview(movieId: String, reviewId: String) {
        viewModelScope.launch {
            deleteReviewUseCase.execute(bearerToken, movieId, reviewId)
        }
    }


}