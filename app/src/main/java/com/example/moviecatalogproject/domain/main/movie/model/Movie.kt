package com.example.moviecatalogproject.domain.main.movie.model

import com.google.gson.annotations.SerializedName

data class Movie(
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
    var reviews: ArrayList<Review>?
)
