package com.example.moviecatalogproject.domain.common.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    var id: String,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("reviewText")
    var reviewText: String?,
    @SerializedName("isAnonymous")
    var isAnonymous: Boolean,
    @SerializedName("createDateTime")
    var createDateTime: String,
    @SerializedName("author")
    var author: UserShort
)
