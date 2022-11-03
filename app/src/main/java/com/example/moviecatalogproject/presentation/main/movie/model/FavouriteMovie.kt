package com.example.moviecatalogproject.presentation.main.movie.model

import com.example.moviecatalogproject.domain.main.movie.model.Movie

data class FavouriteMovie(
    var id: String,
    var movie: Movie,
    var removeFromFavourites: (() -> Unit)?,
    var onMovieClick: (() -> Unit)?
)
