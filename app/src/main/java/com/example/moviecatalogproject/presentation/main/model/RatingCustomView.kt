package com.example.moviecatalogproject.presentation.main.model

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

    fun changeRatingView(rating: Double) {
        setRating(rating)
        changeBackgroundColor(rating)
    }

    private fun setRating(rating: Double) {
        binding.ratingTextView.text = rating.toString()
    }

    private fun changeBackgroundColor(rating: Double) {
        val startColor = ContextCompat.getColor(context, R.color.start_rating_color)
        val endColor = ContextCompat.getColor(context, R.color.end_rating_color)
        val color = ColorUtils.blendARGB(startColor, endColor, (rating / 10).toFloat())
        binding.ratingBackgroundView.setCardBackgroundColor(color)
    }

}