package com.example.moviecatalogproject.presentation.movie_info

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.databinding.ActivityMovieInfoBinding

class MovieInfoActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie id"
    }

    private val binding by lazy {
        ActivityMovieInfoBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        binding.button.visibility = View.GONE

        binding.toolbarLayout.title = ":3"

        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (kotlin.math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                if (binding.button.visibility == View.GONE) {
                    binding.button.visibility = View.VISIBLE
                }
            } else {
                if (binding.button.visibility == View.VISIBLE) {
                    binding.button.visibility = View.GONE
                }
            }
        }


        binding.toolbar.setNavigationOnClickListener { finish() }

    }
}