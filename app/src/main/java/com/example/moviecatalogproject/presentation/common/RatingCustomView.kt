package com.example.moviecatalogproject.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.RatingCustomViewBinding

class RatingCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {
    private val ratingView: View = LayoutInflater
        .from(context)
        .inflate(R.layout.rating_custom_view, this)
    private val binding = RatingCustomViewBinding.bind(ratingView)

    fun changeRatingView(rating: Double, reviewsAmount: Int) {
        setRating(rating, reviewsAmount)
        changeBackgroundColor(rating, reviewsAmount)
    }

    private fun setRating(rating: Double, reviewsAmount: Int) {
        if (reviewsAmount == 0) {
            binding.ratingTextView.text = "—"
        } else {
            binding.ratingTextView.text = rating.toString()
        }

    }

    private fun changeBackgroundColor(rating: Double, reviewsAmount: Int) {
        if (reviewsAmount == 0) {
            binding.ratingBackgroundView.setCardBackgroundColor(
                resources.getColor(
                    R.color.gray,
                    context.theme
                )
            )
        } else {
            val startColor = ContextCompat.getColor(context, R.color.start_rating_color)
            val endColor = ContextCompat.getColor(context, R.color.end_rating_color)
            val color = ColorUtils.blendARGB(startColor, endColor, (rating / 10).toFloat())
            binding.ratingBackgroundView.setCardBackgroundColor(color)
        }

    }

}