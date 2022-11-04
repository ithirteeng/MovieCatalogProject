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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupToolbar()
        onAppbarOffsetChange()

        binding.tableLayout.setColumnShrinkable(1, true)
        binding.toolbarLayout.title = "SHiiiiiiititt"


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
            binding.button.alpha =
                abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            binding.button.isEnabled = binding.button.alpha >= 0.7
        }
    }
}