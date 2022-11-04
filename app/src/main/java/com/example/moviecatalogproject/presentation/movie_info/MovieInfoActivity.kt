package com.example.moviecatalogproject.presentation.movie_info

import android.os.Bundle
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        //binding.tableLayout.setColumnShrinkable(1, true)

        binding.button.visibility = View.VISIBLE

        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            binding.button.alpha =
                abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()

            binding.button.isEnabled = binding.button.alpha >= 0.7
        }

        binding.toolbarLayout.title = "SHiiiiiiititt"

        binding.toolbar.setNavigationOnClickListener { finish() }

    }
}