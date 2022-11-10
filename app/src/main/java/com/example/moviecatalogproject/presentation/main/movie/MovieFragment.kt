package com.example.moviecatalogproject.presentation.main.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentMovieBinding
import com.example.moviecatalogproject.presentation.common.RefreshableFragment
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.main.movie.adapter.CenterZoomLinearLayoutManager
import com.example.moviecatalogproject.presentation.main.movie.adapter.FavouritesAdapter
import com.example.moviecatalogproject.presentation.main.movie.adapter.GalleryAdapter
import com.example.moviecatalogproject.presentation.main.movie.model.FavouriteMovie
import com.example.moviecatalogproject.presentation.main.movie.model.GalleryMovie
import com.example.moviecatalogproject.presentation.movie_info.MovieInfoActivity


class MovieFragment(
    private val changeProgressBarVisibility: (state: Boolean) -> Unit,
    private val changeSwipeToRefreshState: (state: Boolean) -> Unit,
    private val changeSwipeToRefreshRefreshingState: (state: Boolean) -> Unit
) : Fragment(), RefreshableFragment {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel by lazy {
        MovieFragmentViewModel(activity?.application!!) {
            changeProgressBarVisibility(false)
            changeSwipeToRefreshRefreshingState(false)
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.connection_swipe_text),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private val galleryAdapter by lazy {
        GalleryAdapter {
            getNextMoviesList(it)
        }
    }

    private val favouritesAdapter by lazy {
        FavouritesAdapter {
            binding.favouritesTextView.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val mainView = inflater.inflate(R.layout.fragment_movie, container, false)
        binding = FragmentMovieBinding.bind(mainView)
        onAppbarOffsetChange()

        return mainView
    }

    override fun onStart() {
        super.onStart()

        changeProgressBarVisibility(true)
        setupFavouritesRecyclerView()
        setupGalleryRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        changeProgressBarVisibility(true)
        favouritesAdapter.clearMovieList()
        galleryAdapter.clearMovieList()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setupFavouritesRecyclerView() {
        val favouritesRecyclerView = binding.favouritesRecyclerView

        favouritesRecyclerView.layoutManager = CenterZoomLinearLayoutManager(
            requireContext(), 1.3f, 0.3f
        )

        favouritesRecyclerView.adapter = favouritesAdapter

        viewModel.getFavouritesList {
            onErrorAppearanceFunction(it)
        }

        viewModel.getFavouritesLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                val favouritesList = it
                changeFavouritesTextViewVisibility(favouritesList)
                addOnFavouritesCloseButtonFunction(favouritesList)
                addOnFavouritesClickFunction(favouritesList)
                favouritesAdapter.addMovies(favouritesList)
            }
            favouritesRecyclerView.adapter?.notifyDataSetChanged()
        }

        changeSwipeToRefreshRefreshingState(false)
    }

    private fun changeFavouritesTextViewVisibility(favouritesArrayList: ArrayList<FavouriteMovie>) {
        if (favouritesArrayList.size == 0) {
            binding.favouritesTextView.visibility = View.GONE
        } else {
            binding.favouritesTextView.visibility = View.VISIBLE
        }
    }

    private fun addOnFavouritesCloseButtonFunction(favouritesArrayList: ArrayList<FavouriteMovie>) {
        for (movie in favouritesArrayList) {
            movie.removeFromFavourites = {
                viewModel.deleteMovieFromFavourites(movie.id) {
                    onErrorAppearanceFunction(it)
                }
            }
        }
    }

    private fun addOnFavouritesClickFunction(favouritesArrayList: ArrayList<FavouriteMovie>) {
        for (movie in favouritesArrayList) {
            movie.onMovieClick = { movieId ->
                makeIntentToMovieInfoActivity(movieId)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupGalleryRecyclerView() {
        val galleryRecyclerView = binding.galleryRecyclerView

        galleryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        galleryRecyclerView.adapter = galleryAdapter


        viewModel.getMoviesList(1) {
            onErrorAppearanceFunction(it)
        }

        viewModel.getGalleryMoviesLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                val galleryList = copyArray(it)
                if (galleryList[0].page == 1) {

                    binding.watchMovieButton.setOnClickListener { _ ->
                        makeIntentToMovieInfoActivity(it[0].movie.id)
                    }

                    setBannerImage(galleryList[0].movie.poster!!)
                    galleryList.removeFirst()
                }

                addOnGalleryMovieClickFunction(galleryList)
                galleryAdapter.addMovies(galleryList)
            }
            galleryRecyclerView.adapter?.notifyDataSetChanged()
            changeProgressBarVisibility(false)
        }

        changeSwipeToRefreshRefreshingState(false)
    }

    private fun copyArray(movieArray: ArrayList<GalleryMovie>): ArrayList<GalleryMovie> {
        val array = arrayListOf<GalleryMovie>()
        for (movie in movieArray) {
            array.add(movie)
        }
        return array
    }

    private fun getNextMoviesList(page: Int) {
        viewModel.getMoviesList(page) {
            onErrorAppearanceFunction(it)
        }
    }

    private fun onAppbarOffsetChange() {
        binding.appbar.addOnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset == 0) {
                changeSwipeToRefreshState(true)
            } else {
                changeSwipeToRefreshState(false)
            }
        }
    }

    private fun addOnGalleryMovieClickFunction(galleryMoviesList: ArrayList<GalleryMovie>) {
        for (movie in galleryMoviesList) {
            movie.onMovieClick = { movieId ->
                makeIntentToMovieInfoActivity(movieId)
            }
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setBannerImage(url: String) {
        Glide.with(requireContext()).load(url).placeholder(
            binding.root.resources.getDrawable(
                R.drawable.default_movie_poster,
                binding.root.context.theme
            )
        ).error(
            binding.root.resources.getDrawable(
                R.drawable.default_movie_poster,
                binding.root.context.theme
            )
        ).into(binding.bannerImageView)
    }


    private fun onErrorAppearanceFunction(errorCode: Int) {
        if (errorCode == 401) {
            makeIntentToEntranceActivity()
        }
    }

    private fun makeIntentToEntranceActivity() {
        startActivity(Intent(activity, EntranceActivity::class.java))
        activity?.overridePendingTransition(0, 0)
        activity?.finish()
    }

    private fun makeIntentToMovieInfoActivity(movieId: String) {
        val intent = Intent(activity, MovieInfoActivity::class.java)
        activity?.overridePendingTransition(0, 0)
        intent.putExtra(MovieInfoActivity.MOVIE_ID, movieId)
        startActivity(intent)
    }

    override fun refresh() {
        viewModel.setCanOnFailureBeCalled(true)
        favouritesAdapter.clearMovieList()
        galleryAdapter.clearMovieList()
        setupFavouritesRecyclerView()
        setupGalleryRecyclerView()

    }
}

