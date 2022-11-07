package com.example.moviecatalogproject.domain.main.profile.model

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("id")
    var id: String,
    @SerializedName("nickName")
    var nickName: String?,
    @SerializedName("email")
    var email: String,
    @SerializedName("avatarLink")
    var avatarLink: String?,
    @SerializedName("name")
    var name: String,
    @SerializedName("birthDate")
    var birthDate: String,
    @SerializedName("gender")
    var gender: Int
)
