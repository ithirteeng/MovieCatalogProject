package com.example.moviecatalogproject.domain.entrance.registration.model

import com.google.gson.annotations.SerializedName

data class RegistrationData(
    @SerializedName("userName")
    var username: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("birthDate")
    var date: String,
    @SerializedName("gender")
    var gender: Int
)