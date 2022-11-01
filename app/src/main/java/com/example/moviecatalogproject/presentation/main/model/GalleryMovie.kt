package com.example.moviecatalogproject.presentation.main.model

import com.example.moviecatalogproject.domain.main.movie.model.Movie

data class GalleryMovie(
    var movie: Movie,
    val page: Int,
    var pageAmount: Int,
    var onClick: () -> Unit
) {
    fun countRating(): Double {
        val reviews = movie.reviews
        var ratingAmount = 0
        return if (reviews != null) {
            for (review in reviews) {
                ratingAmount += review.rating
            }
            (ratingAmount / (reviews.size)).toDouble()
        } else {
            8.0
        }
    }
}