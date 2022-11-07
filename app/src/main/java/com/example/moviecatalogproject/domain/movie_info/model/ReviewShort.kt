package com.example.moviecatalogproject.domain.movie_info.model

import com.google.gson.annotations.SerializedName

data class ReviewShort(
    @SerializedName("reviewText")
    var reviewText: String,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("isAnonymous")
    var isAnonymous: Boolean
)
