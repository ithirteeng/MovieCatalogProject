package com.example.moviecatalogproject.presentation.main.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentMovieBinding
import com.example.moviecatalogproject.domain.main.movie.model.Genre
import com.example.moviecatalogproject.domain.main.movie.model.Movie
import com.example.moviecatalogproject.presentation.main.model.FavouriteMovie
import com.example.moviecatalogproject.presentation.main.model.GalleryMovie
import com.example.moviecatalogproject.presentation.main.movie.adapter.CenterZoomLinearLayoutManager
import com.example.moviecatalogproject.presentation.main.movie.adapter.FavouritesAdapter
import com.example.moviecatalogproject.presentation.main.movie.adapter.GalleryAdapter


class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private val favouritesList: ArrayList<FavouriteMovie> = arrayListOf(
        FavouriteMovie(
            "1",
            { Log.d("FAVOURITE", "removed 1") },
            { Log.d("FAVOURITE", "added 1") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ),
        FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        ), FavouriteMovie(
            "4",
            { Log.d("FAVOURITE", "removed 4") },
            { Log.d("FAVOURITE", "added 4") }
        )
    )

    private val movie = Movie(
        id = "sfdf",
        name = "name",
        poster = "shit",
        year = 2003,
        country = "Russia",
        genres = arrayListOf(
            Genre("2", "МОРС"),
            Genre("0", "МОРС3"),
            Genre("1", "МОРС2")
        ),
        null
    )

    private val galleryMoviesList: ArrayList<GalleryMovie> = arrayListOf(
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") },
        GalleryMovie(movie) { Log.d("GALLERY", "id: ${movie.id}") }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainView = inflater.inflate(R.layout.fragment_movie, container, false)
        binding = FragmentMovieBinding.bind(mainView)

        setupFavouritesRecyclerView()
        setupGalleryRecyclerView()

        return mainView
    }

    private fun setupFavouritesRecyclerView() {
        val favouritesRecyclerView = binding.favouritesRecyclerView
        favouritesRecyclerView.layoutManager = CenterZoomLinearLayoutManager(
            requireContext(),
            1.3f,
            0.3f
        )

        val favouritesAdapter = FavouritesAdapter()

        favouritesAdapter.setFavouritesList(favouritesList)
        favouritesRecyclerView.adapter = favouritesAdapter
    }

    private fun setupGalleryRecyclerView() {
        val galleryRecyclerView = binding.galleryRecyclerView
        galleryRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val galleryAdapter = GalleryAdapter()

        galleryAdapter.setGalleryMovieList(galleryMoviesList)
        galleryRecyclerView.adapter = galleryAdapter
    }

}