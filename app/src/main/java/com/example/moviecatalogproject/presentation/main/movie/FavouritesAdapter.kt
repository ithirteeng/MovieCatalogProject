package com.example.moviecatalogproject.presentation.main.movie

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogproject.databinding.FavouritesItemBinding

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavouritesItemBinding.bind(view)

        fun onRemoveButtonClick() {
            binding.closeButton.setOnClickListener {
                // TODO: remove from favourites
                Log.d("RECYCLER", "removed from position $absoluteAdapterPosition")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}