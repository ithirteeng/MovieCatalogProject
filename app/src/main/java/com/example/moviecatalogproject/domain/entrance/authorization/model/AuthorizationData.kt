package com.example.moviecatalogproject.domain.entrance.authorization.model

import com.google.gson.annotations.SerializedName

data class AuthorizationData(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
)