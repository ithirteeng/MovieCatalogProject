package com.example.moviecatalogproject.presentation.main.model

data class FavouriteMovie(
    var id: String,
    //var movie: Movie,
    var removeFromFavourites: () -> Unit,
    var onMovieClick: () -> Unit
)
