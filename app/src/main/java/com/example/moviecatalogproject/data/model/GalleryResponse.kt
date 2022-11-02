package com.example.moviecatalogproject.data.model

import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.example.moviecatalogproject.domain.main.movie.model.PageInfo
import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("movies")
    var movies: ArrayList<Movie>,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo
)