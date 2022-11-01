package com.example.moviecatalogproject.domain.main.movie.model

import com.google.gson.annotations.SerializedName

data class UserShort(
    @SerializedName("userId")
    var userId: String,
    @SerializedName("nickName")
    var nickname: String?,
    @SerializedName("avatat")
    var avatar: String?
)
