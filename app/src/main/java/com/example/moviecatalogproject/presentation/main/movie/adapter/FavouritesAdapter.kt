package com.example.moviecatalogproject.presentation.main.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FavouritesItemBinding
import com.example.moviecatalogproject.presentation.main.movie.model.FavouriteMovie

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavouritesItemBinding.bind(view)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun setImage(favouriteMovie: FavouriteMovie) {
            Glide
                .with(binding.root)
                .load(favouriteMovie.movie.poster)
                .placeholder(
                    binding.root.resources.getDrawable(
                        R.drawable.default_movie_poster, binding.root.context.theme
                    )
                )
                .error(
                    binding.root.resources.getDrawable(
                        R.drawable.default_movie_poster, binding.root.context.theme
                    )
                )
                .into(binding.filmImageView)
        }

        fun onCloseButtonClick(onCloseButtonClick: () -> Unit) {
            binding.closeButton.setOnClickListener {
                onCloseButtonClick()
            }
        }

        fun onMovieClick(onMovieClick: () -> Unit) {
            binding.root.setOnClickListener {
                onMovieClick()
            }
        }


    }

    private var favouritesList = arrayListOf<FavouriteMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.favourites_item, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val item = favouritesList[position]
        holder.setImage(item)
        holder.onCloseButtonClick {
            item.removeFromFavourites?.invoke()
            removeItemAt(position)
        }
        holder.onMovieClick {
            item.onMovieClick?.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    private fun removeItemAt(position: Int) {
        favouritesList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favouritesList.size)
    }

    fun setFavouritesList(movieList: ArrayList<FavouriteMovie>) {
        favouritesList = movieList
    }

}