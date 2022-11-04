package com.example.moviecatalogproject.presentation.movie_info

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivityMovieInfoBinding
import com.example.moviecatalogproject.domain.main.movie.model.Genre
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import java.text.NumberFormat
import java.util.*
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
        getMovieDetails()


        binding.tableLayout.setColumnShrinkable(1, true)

    }

    @SuppressLint("SetTextI18n")
    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId, completeOnError = {
            onErrorAppearanceFunction(it)
        })

        viewModel.getMovieDetailsLiveData().observe(this) { movieDetails ->
            setBannerImage(movieDetails.poster!!)
            binding.toolbarLayout.title = movieDetails.name
            binding.descriptionTextView.text = movieDetails.description
            binding.yearTextView.text = makeStringCorrect(movieDetails.year)
            binding.countryTextView.text = makeStringCorrect(movieDetails.country)
            binding.timeTextView.text = makeStringCorrect(movieDetails.time)
            binding.sloganTextView.text = makeStringCorrect(movieDetails.slogan)
            binding.directorTextView.text = makeStringCorrect(movieDetails.director)
            binding.budgetTextView.text = makeMoneyStringsCorrect(movieDetails.budget)
            binding.feesTextView.text = makeMoneyStringsCorrect(movieDetails.fees)
            binding.ageTextView.text = makeStringCorrect(movieDetails.age) + "+"
            setGenres(movieDetails.genres!!)
        }
    }

    private fun setGenres(genresList: ArrayList<Genre>) {
        for (genre in genresList) {
            val textView = TextView(ContextThemeWrapper(this, R.style.genre_style))
            textView.text = genre.name
            binding.flexBox.addView(textView)
            setTextViewMargin(textView)
        }
    }

    private fun setTextViewMargin(textView: TextView) {
        val params = textView.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = 20
        params.marginEnd = 20

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setBannerImage(imageUrl: String) {
        Glide.with(this).load(imageUrl).placeholder(
            binding.root.resources.getDrawable(
                R.drawable.default_movie_poster,
                binding.root.context.theme
            )
        ).error(
            binding.root.resources.getDrawable(
                R.drawable.default_movie_poster,
                binding.root.context.theme
            )
        ).into(binding.appBarImage)
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

    private fun makeStringCorrect(string: String?): String {
        return string ?: "—"
    }

    private fun makeStringCorrect(number: Int?): String {
        return number?.toString() ?: "—"
    }

    private fun makeMoneyStringsCorrect(money: Int?): String {
        return if (money == null) {
            "—"
        } else {
            "$" + NumberFormat
                .getNumberInstance(Locale.US)
                .format(money)
                .replace(",", " ")
        }
    }

}

