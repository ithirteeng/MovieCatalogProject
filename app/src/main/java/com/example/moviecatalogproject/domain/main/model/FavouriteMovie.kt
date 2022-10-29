package com.example.moviecatalogproject.domain.main.model

data class FavouriteMovie(
    var id: String,
    //var movie: Movie,
    var removeFromFavourites: () -> Unit,
    var setImage: () -> Unit
)
