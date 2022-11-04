package com.example.moviecatalogproject.domain.movie_info.model

import com.example.moviecatalogproject.domain.common.model.Review
import com.example.moviecatalogproject.domain.main.movie.model.Genre
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String?,
    @SerializedName("poster")
    var poster: String?,
    @SerializedName("year")
    var year: Int,
    @SerializedName("country")
    var country: String?,
    @SerializedName("genres")
    var genres: ArrayList<Genre>?,
    @SerializedName("reviews")
    var reviews: ArrayList<Review>?,
    @SerializedName("time")
    var time: Int,
    @SerializedName("tagline")
    var slogan: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("director")
    var director: String?,
    @SerializedName("budget")
    var budget: Int?,
    @SerializedName("fees")
    var fees: Int?,
    @SerializedName("ageLimit")
    var age: Int,

)