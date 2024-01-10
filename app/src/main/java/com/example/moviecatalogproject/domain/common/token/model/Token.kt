package com.example.moviecatalogproject.domain.common.token.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    var token: String
)
