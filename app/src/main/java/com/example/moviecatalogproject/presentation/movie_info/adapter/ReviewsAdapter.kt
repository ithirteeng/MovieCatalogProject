package com.example.moviecatalogproject.presentation.movie_info.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ReviewItemBinding
import com.example.moviecatalogproject.domain.common.model.Review
import com.example.moviecatalogproject.presentation.movie_info.model.ExpandedReview

class ReviewsAdapter(private val userId: String) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    class ReviewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ReviewItemBinding.bind(view)

        fun onRedactButtonClick(onClick: () -> Unit) {
            binding.redactReviewButton.setOnClickListener {
                onClick()
            }
        }

        fun onDeleteButtonClick(onClick: () -> Unit) {
            binding.removeReviewButton.setOnClickListener {
                onClick()
            }
        }

        fun bind(review: Review) {
            if (!review.isAnonymous) {
                binding.userOwnReviewTextView.visibility = View.GONE
                binding.usernameTextView.text = review.author.nickname
                setAvatarImage(review.author.avatar)
            } else {
                setAvatarImage("")
                binding.userOwnReviewTextView.visibility = View.GONE
                binding.usernameTextView.text =
                    binding.root.resources.getString(R.string.anonymous_username)
            }

            binding.reviewDateTextView.text = review.createDateTime
            binding.ratingCustomView.changeRatingView(review.rating)
            binding.reviewTextView.text = review.reviewText
        }

        fun changeButtonsOnReviewOwnership(userId: String, review: Review) {
            if (!review.isAnonymous && userId == review.author.userId) {
                binding.removeReviewButton.visibility = View.VISIBLE
                binding.redactReviewButton.visibility = View.VISIBLE
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun setAvatarImage(url: String?) {
            Glide
                .with(binding.root)
                .load(url)
                .placeholder(
                    binding.root.resources.getDrawable(
                        R.drawable.default_movie_poster, binding.root.context.theme
                    )
                )
                .error(
                    binding.root.resources.getDrawable(
                        R.drawable.default_user_avatar_image, binding.root.context.theme
                    )
                )
                .into(binding.avatarImageView)
        }
    }

    private var reviewsList = arrayListOf<ExpandedReview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val item = reviewsList[position]

        holder.bind(item.review)
        holder.changeButtonsOnReviewOwnership(userId, item.review)

        holder.onRedactButtonClick {
            item.onRedactButtonClick?.invoke()
        }
        holder.onDeleteButtonClick {
            item.onDeleteButtonClick?.invoke()
        }

    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    fun addItemsToReviewsList(newReviewsList: ArrayList<ExpandedReview>) {
        for (review in newReviewsList) {
            reviewsList.add(review)
            notifyItemInserted(reviewsList.size - 1)
        }
    }
}