package com.example.moviecatalogproject.presentation.main.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.common.token.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.main.movie.usecase.DeleteFromFavouritesUseCase
import com.example.moviecatalogproject.domain.main.movie.usecase.GetFavouritesListUseCase
import com.example.moviecatalogproject.domain.main.movie.usecase.GetMoviesListUseCase
import com.example.moviecatalogproject.presentation.common.SingleEventLiveData
import com.example.moviecatalogproject.presentation.main.movie.model.FavouriteMovie
import com.example.moviecatalogproject.presentation.main.movie.model.GalleryMovie
import kotlinx.coroutines.launch

class MovieFragmentViewModel(
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


    private val getMoviesListUseCase = GetMoviesListUseCase()
    private val galleryMoviesLiveData = SingleEventLiveData<ArrayList<GalleryMovie>?>()
    fun getMoviesList(page: Int, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            getMoviesListUseCase.execute(page, token, completeOnError).onSuccess {
                canOnFailureBeCalled = true
                galleryMoviesLiveData.value = it
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

    fun getGalleryMoviesLiveData(): LiveData<ArrayList<GalleryMovie>?> {
        return galleryMoviesLiveData
    }


    private val getFavouritesListUseCase = GetFavouritesListUseCase()
    private val favouritesListLiveData = SingleEventLiveData<ArrayList<FavouriteMovie>?>()
    fun getFavouritesList(completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            getFavouritesListUseCase.execute(token, completeOnError).onSuccess {
                canOnFailureBeCalled = true
                favouritesListLiveData.value = it
            }.onFailure {
                if (canOnFailureBeCalled) {
                    canOnFailureBeCalled = false
                    onInternetConnectionFailure()
                }
            }
        }
    }

    fun getFavouritesLiveData(): LiveData<ArrayList<FavouriteMovie>?> {
        return favouritesListLiveData
    }


    private val deleteFromFavouritesUseCase = DeleteFromFavouritesUseCase()
    fun deleteMovieFromFavourites(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            deleteFromFavouritesUseCase.execute(movieId, token, completeOnError).onSuccess {
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