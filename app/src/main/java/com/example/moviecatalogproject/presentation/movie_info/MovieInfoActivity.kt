package com.example.moviecatalogproject.presentation.movie_info

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivityMovieInfoBinding
import com.example.moviecatalogproject.domain.common.model.Review
import com.example.moviecatalogproject.domain.main.movie.model.Genre
import com.example.moviecatalogproject.domain.movie_info.model.MovieDetails
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.movie_info.adapter.ReviewsAdapter
import com.example.moviecatalogproject.presentation.movie_info.dialog.CustomDialogFragment
import com.example.moviecatalogproject.presentation.movie_info.helper.ReviewMapper
import com.example.moviecatalogproject.presentation.movie_info.model.CreatingDialogReason
import com.example.moviecatalogproject.presentation.movie_info.model.ExpandedReview
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

    private val reviewsAdapter = ReviewsAdapter()

    private val dialogFragment by lazy {
        CustomDialogFragment(movieId, completeOnAddingReview = {
            reviewsAdapter.clearReviewsList()
            binding.flexBox.removeAllViews()
            binding.progressBar.visibility = View.VISIBLE
            getMovieDetails()
        })
    }

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        movieId = intent.getStringExtra(MOVIE_ID)!!

        binding.progressBar.visibility = View.VISIBLE

        setSupportActionBar(binding.toolbar)
        setupToolbar()
        setupLikeButtonFunctions()
        setupDialogFragment()

        onAddReviewButtonClick()
        onAppbarOffsetChange()

        getUserId()
        onGettingMovieDetails()

        binding.tableLayout.setColumnShrinkable(1, true)

    }

    private fun setupDialogFragment() {
        dialogFragment.dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogFragment.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme)
    }

    private fun onGettingMovieDetails() {
        getMovieDetails()
        viewModel.getMovieDetailsLiveData().observe(this) { movieDetails ->
            setMovieDetails(movieDetails)
            binding.progressBar.visibility = View.GONE
            onGettingUserId(movieDetails.reviews!!)
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId, completeOnError = {
            onErrorAppearanceFunction(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieDetails(movieDetails: MovieDetails) {
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
        if (this.userId != null) {
            setupReviewsRecyclerView(movieDetails.reviews!!)
            val ifUserReviewExists = checkUserReviewExisting(movieDetails.reviews!!, this.userId!!)
            changeAddReviewButtonVisibility(ifUserReviewExists)
        } else {
            onGettingUserId(movieDetails.reviews!!)
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
            val alfa = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            binding.likeButton.alpha = alfa
            binding.likeButton.isEnabled = binding.likeButton.alpha >= 0.7
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupReviewsRecyclerView(reviewsList: ArrayList<Review>) {
        val recyclerView = binding.reviewRecyclerView
        recyclerView.adapter = reviewsAdapter
        val expandedReviewsList = ReviewMapper.reviewsArrayListToExpandedReviewsArrayList(
            reviewsList
        )
        addOnReviewButtonClickFunctions(expandedReviewsList)

        reviewsAdapter.addItemsToReviewsList(expandedReviewsList)

        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun onGettingUserId(reviewsList: ArrayList<Review>) {
        viewModel.getUserIdLiveData().observe(this) {
            val ifUserReviewExists = checkUserReviewExisting(reviewsList, it)
            changeAddReviewButtonVisibility(ifUserReviewExists)
            reviewsAdapter.setUserId(it)
            setupReviewsRecyclerView(reviewsList)
            this.userId = it
        }
    }

    private fun getUserId() {
        viewModel.getUserId {
            onErrorAppearanceFunction(it)
        }
    }

    private fun changeAddReviewButtonVisibility(ifUserReviewExists: Boolean) {
        if (!ifUserReviewExists) {
            binding.addButton.visibility = View.VISIBLE
        } else {
            binding.addButton.visibility = View.GONE
        }
    }

    private fun addOnReviewButtonClickFunctions(expandedReviewsList: ArrayList<ExpandedReview>) {
        for (review in expandedReviewsList) {
            addOnDeleteReviewFunction(review)
            addOnChangeReviewFunctions(review)
        }
    }

    private fun addOnDeleteReviewFunction(expandedReview: ExpandedReview) {
        expandedReview.onDeleteButtonClick = { reviewId ->
            viewModel.deleteReview(movieId, reviewId)
            binding.addButton.visibility = View.VISIBLE
        }
    }

    private fun addOnChangeReviewFunctions(expandedReview: ExpandedReview) {
        expandedReview.onRedactButtonClick = { reviewId ->
            dialogFragment.setReviewId(reviewId)
            dialogFragment.setCreatingReason(CreatingDialogReason.CHANGE_REVIEW)
            dialogFragment.setReviewShortInfo(
                ReviewShort(
                    expandedReview.review.reviewText!!,
                    expandedReview.review.rating,
                    expandedReview.review.isAnonymous
                )
            )
            dialogFragment.show(supportFragmentManager, "review_dialog")
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
            binding.likeButton.setCorrectBackground()
        }
    }

    private fun onLikeButtonClick() {
        binding.likeButton.onClick {
            if (binding.likeButton.isButtonFilled()) {
                addToFavourites()
            } else {
                deleteFromFavourites()
            }
        }
    }

    private fun deleteFromFavourites() {
        viewModel.deleteFromFavourites(movieId, completeOnError = {
            onErrorAppearanceFunction(it)
        })
    }

    private fun addToFavourites() {
        viewModel.addToFavourites(movieId, completeOnError = {
            onErrorAppearanceFunction(it)
        })
    }


    private fun onAddReviewButtonClick() {
        binding.addButton.setOnClickListener {
            dialogFragment.setCreatingReason(CreatingDialogReason.ADD_REVIEW)
            dialogFragment.show(supportFragmentManager, "review_dialog")
        }
    }

    private fun checkUserReviewExisting(reviewsList: ArrayList<Review>, userId: String): Boolean {
        for (review in reviewsList) {
            try {
                if (review.author.userId == userId) {
                    return true
                }
            } catch (_: Exception) {
            }
        }
        return false
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
        string?.replace("\n", " ")
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

