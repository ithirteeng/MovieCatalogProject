package com.example.moviecatalogproject.presentation.main.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogproject.domain.main.movie.usecase.DeleteFromFavouritesUseCase
import com.example.moviecatalogproject.domain.main.movie.usecase.GetFavouritesListUseCase
import com.example.moviecatalogproject.domain.main.movie.usecase.GetMoviesListUseCase
import com.example.moviecatalogproject.domain.main.profile.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalogproject.domain.model.Token
import com.example.moviecatalogproject.presentation.model.SingleEventLiveData
import com.example.moviecatalogproject.presentation.main.model.FavouriteMovie
import com.example.moviecatalogproject.presentation.main.model.GalleryMovie
import kotlinx.coroutines.launch

class MovieFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val getTokenFromLocalStorageUseCase =
        GetTokenFromLocalStorageUseCase(application.applicationContext)

    private val token by lazy {
        getTokenFromLocalStorageUseCase.execute()
    }

    private val bearerToken by lazy {
        Token("Bearer ${token.token}")
    }

    private val getMoviesListUseCase = GetMoviesListUseCase()
    private val galleryMoviesLiveData = SingleEventLiveData<ArrayList<GalleryMovie>?>()


    fun getMoviesList(page: Int, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            galleryMoviesLiveData.value =
                getMoviesListUseCase.execute(page, bearerToken, completeOnError)
        }
    }

    fun getGalleryMoviesLiveData(): MutableLiveData<ArrayList<GalleryMovie>?> {
        return galleryMoviesLiveData
    }

    private val getFavouritesListUseCase = GetFavouritesListUseCase()
    private val favouritesListLiveData = SingleEventLiveData<ArrayList<FavouriteMovie>?>()

    fun getFavouritesList(completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            favouritesListLiveData.value =
                getFavouritesListUseCase.execute(bearerToken, completeOnError)
        }
    }

    fun getFavouritesLiveData(): MutableLiveData<ArrayList<FavouriteMovie>?> {
        return favouritesListLiveData
    }

    private val deleteFromFavouritesUseCase = DeleteFromFavouritesUseCase()

    fun deleteMovieFromFavourites(movieId: String, completeOnError: (errorCode: Int) -> Unit) {
        viewModelScope.launch {
            deleteFromFavouritesUseCase.execute(movieId, bearerToken, completeOnError)
        }
    }


}