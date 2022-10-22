package com.example.moviecatalogproject.domain.entrance.registration.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class RegistrationData(
    @SerializedName("userName")
    var username: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("birthDate")
    var date: Timestamp,
    @SerializedName("gender")
    var gender: Int
)