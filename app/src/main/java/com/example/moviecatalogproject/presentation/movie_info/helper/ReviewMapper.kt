package com.example.moviecatalogproject.presentation.movie_info.helper

import com.example.moviecatalogproject.domain.common.model.Review
import com.example.moviecatalogproject.presentation.movie_info.model.ExpandedReview

object ReviewMapper {
    fun reviewsArrayListToExpandedReviewsArrayList(reviewsList: ArrayList<Review>): ArrayList<ExpandedReview> {
        val expandedReviewsList = arrayListOf<ExpandedReview>()
        for (review in reviewsList) {
            expandedReviewsList.add(ExpandedReview(review, null, null))
        }
        return expandedReviewsList
    }
}