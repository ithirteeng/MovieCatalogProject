package com.example.moviecatalogproject.domain.main.model

data class Review(
    var id: String,
    var rating: Int,
    var reviewText: String?,
    var isAnonymous: Boolean,
    var createDateTime: String,
    var author: UserShort
)
