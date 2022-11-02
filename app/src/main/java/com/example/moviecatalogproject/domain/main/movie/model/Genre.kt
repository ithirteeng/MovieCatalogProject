package com.example.moviecatalogproject.domain.main.movie.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String?
)
