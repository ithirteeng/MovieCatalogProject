package com.example.moviecatalogproject.presentation.movie_info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.databinding.ActivityMovieInfoBinding
import kotlin.math.abs

class MovieInfoActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie id"
    }

    private val binding by lazy {
        ActivityMovieInfoBinding.inflate(this.layoutInflater)
    }

    private val movieId by lazy {
        intent.getStringExtra(MOVIE_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupToolbar()
        onAppbarOffsetChange()

        binding.tableLayout.setColumnShrinkable(1, true)

    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun onAppbarOffsetChange() {
        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.likeButton.alpha =
                abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            binding.likeButton.isEnabled = binding.likeButton.alpha >= 0.7
        }
    }

    private fun onLikeButtonClick() {
        binding.likeButton.onClick {
            //send request
        }
    }
}

