package com.example.moviecatalogproject.presentation.movie_info

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.databinding.ActivityMovieInfoBinding
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import kotlin.math.abs

class MovieInfoActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie id"
    }


    private val binding by lazy {
        ActivityMovieInfoBinding.inflate(this.layoutInflater)
    }

    private lateinit var movieId: String

    private val viewModel by lazy {
        MovieInfoActivityViewModel(application)
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        movieId = intent.getStringExtra(MOVIE_ID)!!

        setSupportActionBar(binding.toolbar)
        setupToolbar()
        onAppbarOffsetChange()
        setupLikeButtonFunctions()


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

    private fun setupLikeButtonFunctions() {
        setLikeButtonState()
        onLikeButtonClick()
    }

    private fun setLikeButtonState() {
        viewModel.checkIsMovieFavourite(movieId, completeOnError = {
            onErrorAppearanceFunction(it)
        })

        viewModel.getCheckingIsMovieFavouriteResultLiveData().observe(this) {
            binding.likeButton.setButtonFavouriteState(it)
            binding.likeButton.setButtonCorrectBackground()
        }
    }

    private fun onLikeButtonClick() {
        binding.likeButton.onClick {
            //send request
        }
    }

    private fun onErrorAppearanceFunction(errorCode: Int) {
        if (errorCode == 401) {
            makeIntentToEntranceActivity()
        }
    }

    private fun makeIntentToEntranceActivity() {
        startActivity(Intent(this, EntranceActivity::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }
}

