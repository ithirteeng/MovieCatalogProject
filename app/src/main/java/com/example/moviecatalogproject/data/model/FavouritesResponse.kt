package com.example.moviecatalogproject.data.model

import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.google.gson.annotations.SerializedName

data class FavouritesResponse(
    @SerializedName("movies")
    var movies: ArrayList<Movie>
)
