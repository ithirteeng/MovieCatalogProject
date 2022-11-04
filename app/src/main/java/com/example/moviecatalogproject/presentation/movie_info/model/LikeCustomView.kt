package com.example.moviecatalogproject.presentation.movie_info.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.LikeCustomViewBinding

class LikeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val likeButtonView: View =
        LayoutInflater.from(context).inflate(R.layout.like_custom_view, this)

    private val binding = LikeCustomViewBinding.bind(likeButtonView)

    private var isFavourite = false

    fun setButtonFavouriteState(state: Boolean) {
        isFavourite = state
    }

    fun onClick(addToFavourites: () -> Unit) {
        binding.button.setOnClickListener {
            isFavourite = !isFavourite
            setButtonCorrectBackground()
            addToFavourites()
        }
    }

    fun setButtonCorrectBackground() {
        if (isFavourite) {
            setButtonBackground(R.drawable.like_filled)
        } else {
            setButtonBackground(R.drawable.like_outline)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setButtonBackground(drawableId: Int) {
        binding.button.setImageDrawable(
            resources.getDrawable(drawableId, context.theme)
        )
    }

}