package com.example.moviecatalogproject.presentation.main.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentMovieBinding
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.main.model.FavouriteMovie
import com.example.moviecatalogproject.presentation.main.movie.adapter.CenterZoomLinearLayoutManager
import com.example.moviecatalogproject.presentation.main.movie.adapter.FavouritesAdapter
import com.example.moviecatalogproject.presentation.main.movie.adapter.GalleryAdapter


class MovieFragment(val onFragmentStart: () -> Unit) : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel by lazy {
        MovieFragmentViewModel(activity?.application!!)
    }

    private val galleryAdapter by lazy {
        GalleryAdapter {
            getNextMoviesList(it)
        }
    }

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

    override fun onStop() {
        super.onStop()
        galleryAdapter.clearMovieList()
    }

    override fun onStart() {
        super.onStart()
        onFragmentStart()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupFavouritesRecyclerView() {
        val favouritesRecyclerView = binding.favouritesRecyclerView
        favouritesRecyclerView.layoutManager = CenterZoomLinearLayoutManager(
            requireContext(),
            1.3f,
            0.3f
        )

        val favouritesAdapter = FavouritesAdapter()
        favouritesRecyclerView.adapter = favouritesAdapter

        viewModel.getFavouritesList {
            if (it == 401) {
                makeIntentToEntranceActivity()
            }
        }

        viewModel.getFavouritesLiveData().observe(viewLifecycleOwner) {
            var favouritesList = arrayListOf<FavouriteMovie>()
            if (it != null) {
                favouritesList = it
                addOnFavouritesCloseButtonFunction(favouritesList)
            }

            favouritesAdapter.setFavouritesList(favouritesList)
            favouritesRecyclerView.adapter?.notifyDataSetChanged()
        }

    }

    private fun addOnFavouritesCloseButtonFunction(favouritesArrayList: ArrayList<FavouriteMovie>) {
        for (movie in favouritesArrayList) {
            movie.removeFromFavourites = {
                viewModel.deleteMovieFromFavourites(movie.id) { code ->
                    if (code == 401) {
                        makeIntentToEntranceActivity()
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupGalleryRecyclerView() {
        val galleryRecyclerView = binding.galleryRecyclerView

        galleryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        galleryRecyclerView.adapter = galleryAdapter


        viewModel.getMoviesList(1) {
            if (it == 401) {
                makeIntentToEntranceActivity()
            }
        }

        viewModel.getGalleryMoviesLiveData().observe(viewLifecycleOwner) {

            if (it != null) {
                val galleryList = it

                if (galleryList[0].page == 1) {
                    setBannerImage(galleryList[0].movie.poster!!)
                    galleryList.removeFirst()
                }

                galleryAdapter.addMovies(galleryList)
            }
            galleryRecyclerView.adapter?.notifyDataSetChanged()

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getNextMoviesList(page: Int) {
        viewModel.getMoviesList(page) {
            if (it == 401) {
                makeIntentToEntranceActivity()
            }
        }

    }

    private fun makeIntentToEntranceActivity() {
        startActivity(Intent(activity, EntranceActivity::class.java))
        activity?.overridePendingTransition(0, 0)
        activity?.finish()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setBannerImage(url: String) {
        Glide
            .with(requireContext())
            .load(url)
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
            .into(binding.bannerImageView)
    }

}

