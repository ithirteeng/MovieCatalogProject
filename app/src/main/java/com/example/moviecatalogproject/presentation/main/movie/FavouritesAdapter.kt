package com.example.moviecatalogproject.presentation.main.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FavouritesItemBinding
import com.example.moviecatalogproject.domain.main.model.FavouriteMovie

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavouritesItemBinding.bind(view)
        val closeButton = binding.closeButton

        @SuppressLint("UseCompatLoadingForDrawables")
        fun setImage() {
            binding.filmImageView.setImageDrawable(
                binding.root.resources.getDrawable(
                    R.drawable.test_favourites_image,
                    binding.root.context.theme
                )
            )
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
        holder.setImage()
        holder.closeButton.setOnClickListener {
            item.removeFromFavourites()
            removeItemAt(position)
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