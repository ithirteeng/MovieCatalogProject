package com.example.moviecatalogproject.domain.common.model

import com.google.gson.annotations.SerializedName

data class UserShort(
    @SerializedName("userId")
    var userId: String,
    @SerializedName("nickName")
    var nickname: String?,
    @SerializedName("avatar")
    var avatar: String?
)
