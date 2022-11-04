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
import java.text.DecimalFormat

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

    fun changeRatingView(rating: Int) {
        setRating(rating)
        changeBackgroundColor(rating.toDouble(), 1)
    }

    private fun setRating(rating: Int) {
        binding.ratingTextView.text = rating.toString()
    }

    private fun setRating(rating: Double, reviewsAmount: Int) {
        if (reviewsAmount == 0) {
            binding.ratingTextView.text = "—"
        } else {
            val decimalFormat = DecimalFormat("#.#")
            var result = decimalFormat.format(rating)
            if (rating - rating.toInt() == 0.0) {
                result += ".0"
            }
            binding.ratingTextView.text = result
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