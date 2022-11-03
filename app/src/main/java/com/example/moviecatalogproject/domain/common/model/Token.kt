package com.example.moviecatalogproject.domain.common.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    var token: String
)
