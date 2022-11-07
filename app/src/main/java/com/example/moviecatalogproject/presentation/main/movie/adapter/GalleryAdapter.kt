package com.example.moviecatalogproject.presentation.main.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.GalleryItemBinding
import com.example.moviecatalogproject.presentation.main.movie.model.GalleryMovie

class GalleryAdapter(val getGalleryMoviesList: (page: Int) -> Unit) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = GalleryItemBinding.bind(view)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(galleryMovie: GalleryMovie) {
            Glide
                .with(binding.root)
                .load(galleryMovie.movie.poster)
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
                .into(binding.movieImageView)
            binding.movieNameTextView.text = galleryMovie.movie.name!!
            binding.movieYearTextView.text = galleryMovie.movie.year.toString()
            binding.movieCountryTextView.text = galleryMovie.movie.country!!
            binding.movieGenresTextView.text = makeStringOfGenres(galleryMovie)
            binding.ratingView.changeRatingView(
                galleryMovie.countRating(),
                galleryMovie.getReviewsCount()
            )
        }

        fun onMovieCLick(onMovieClick: () -> Unit) {
            binding.root.setOnClickListener {
                onMovieClick()
            }
        }

        private fun makeStringOfGenres(galleryMovie: GalleryMovie): String {
            val genres = galleryMovie.movie.genres
            var result = ""
            if (genres != null) {
                for (index in 0 until genres.size) {
                    result += if (index != genres.size - 1) {
                        "${genres[index].name}, "
                    } else {
                        genres[index].name
                    }
                }
            }
            return result
        }
    }

    private var galleryMoviesList = arrayListOf<GalleryMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = galleryMoviesList[position]
        holder.bind(item)
        holder.onMovieCLick {
            item.onMovieClick?.invoke(item.movie.id)
        }

        if (item.page < item.pageAmount && position == galleryMoviesList.size - 1) {
            getGalleryMoviesList(item.page + 1)
        }
    }

    fun clearMovieList() {
        galleryMoviesList.clear()
    }

    fun addMovies(newMoviesList: ArrayList<GalleryMovie>) {
        for (movie in newMoviesList) {
            galleryMoviesList.add(movie)
            notifyItemInserted(galleryMoviesList.size - 1)
        }
    }

    override fun getItemCount(): Int {
        return galleryMoviesList.size
    }
}