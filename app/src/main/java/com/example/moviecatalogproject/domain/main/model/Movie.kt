package com.example.moviecatalogproject.domain.main.model

data class Movie(
    var id: String,
    var name: String?,
    var poster: String?,
    var year: Int,
    var country: String?,
    var genres: ArrayList<Genre>?,
    var reviews: ArrayList<Review>?
)
