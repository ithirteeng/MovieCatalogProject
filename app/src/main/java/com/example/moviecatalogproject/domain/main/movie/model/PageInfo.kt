package com.example.moviecatalogproject.domain.main.movie.model

import com.google.gson.annotations.SerializedName

data class PageInfo(
    @SerializedName("pageSize")
    var pageSize: Int,
    @SerializedName("pageCount")
    var pageCount: Int,
    @SerializedName("currentPage")
    var currentPage: Int
)
