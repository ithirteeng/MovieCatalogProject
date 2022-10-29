package com.example.moviecatalogproject.presentation.main.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentMovieBinding
import com.example.moviecatalogproject.domain.main.model.FavouriteMovie


class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private val movieList: ArrayList<FavouriteMovie> = arrayListOf(
        FavouriteMovie(
            "1",
            { Log.d("FAVOURITE", "removed 1") },
            { Log.d("FAVOURITE", "added 1") }
        ),
        FavouriteMovie(
            "2",
            { Log.d("FAVOURITE", "removed 2") },
            { Log.d("FAVOURITE", "added 2") }
        ),
        FavouriteMovie(
            "3",
            { Log.d("FAVOURITE", "removed 3") },
            { Log.d("FAVOURITE", "added 3") }
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
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainView = inflater.inflate(R.layout.fragment_movie, container, false)
        binding = FragmentMovieBinding.bind(mainView)

        val favouritesRecyclerView = binding.favouritesRecyclerView
        favouritesRecyclerView.layoutManager = CenterZoomLinearLayoutManager(
            requireContext(),
            1.3f,
            0.3f
        )

        val favouritesAdapter = FavouritesAdapter()

        favouritesAdapter.setFavouritesList(movieList)
        favouritesRecyclerView.adapter = favouritesAdapter

        return mainView
    }

}