package com.example.moviecatalogproject.presentation.movie_info.model

import com.example.moviecatalogproject.domain.common.model.Review

data class ExpandedReview (
    var review: Review,
    var onRedactButtonClick: ((reviewId: String) -> Unit)?,
    var onDeleteButtonClick: ((reviewId: String) -> Unit)?
)
