package com.example.moviecatalogproject.presentation.movie_info.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentCustomDialogBinding
import com.example.moviecatalogproject.domain.movie_info.model.ReviewShort
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.movie_info.model.CreatingDialogReason

class CustomDialogFragment(
    private val movieId: String,
    private val completeOnSavingReview: () -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding

    private val viewModel by lazy {
        CustomDialogFragmentViewModel(activity?.application!!)
    }

    private var creatingReason = CreatingDialogReason.ADD_REVIEW

    private lateinit var reviewId: String

    private lateinit var reviewShortInfo: ReviewShort

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_custom_dialog, null)
        binding = FragmentCustomDialogBinding.bind(view)

        binding.reviewEditText.onEditTextEditorAction()

        onCancelButtonClick()
        onSaveButtonClick()

        if (creatingReason == CreatingDialogReason.CHANGE_REVIEW) {
            bindReviewInfo()
        }

        val builder = AlertDialog.Builder(requireActivity(), R.style.ProgressDialogTheme)
        return builder.setView(view).create()
    }

    fun setReviewId(reviewId: String) {
        this.reviewId = reviewId
    }

    fun setReviewShortInfo(reviewShort: ReviewShort) {
        this.reviewShortInfo = reviewShort
    }

    fun setCreatingReason(creatingDialogReason: CreatingDialogReason) {
        creatingReason = creatingDialogReason
    }

    private fun onCancelButtonClick() {
        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }
    }

    private fun onSaveButtonClick() {
        binding.saveButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (creatingReason == CreatingDialogReason.ADD_REVIEW) {
                addReview()
                onAddingReview()
            } else {
                changeReview()
                onChangingReview()
            }
        }
    }

    private fun addReview() {
        viewModel.addReview(movieId, setupShortReviewInfo(), completeOnError = {
            onErrorAppearanceFunction(it)
        })
    }

    private fun onAddingReview() {
        viewModel.getOnCompleteAddingLiveData().observe(this) {
            completeOnSavingReview()
            this.dismiss()
        }
    }

    private fun changeReview() {
        viewModel.changeReview(
            movieId,
            reviewId,
            setupShortReviewInfo(),
            completeOnError = {
                onErrorAppearanceFunction(it)
            })
    }

    private fun onChangingReview() {
        viewModel.getOnCompleteChangingLiveData().observe(this) {
            completeOnSavingReview()
            binding.progressBar.visibility = View.GONE
            this.dismiss()
        }
    }

    private fun bindReviewInfo() {
        binding.ratingBar.rating = reviewShortInfo.rating.toFloat()
        binding.reviewEditText.setText(reviewShortInfo.reviewText)
        binding.checkBox.isChecked = reviewShortInfo.isAnonymous
    }

    private fun setupShortReviewInfo(): ReviewShort {
        return ReviewShort(
            binding.reviewEditText.text.toString(),
            binding.ratingBar.rating.toInt(),
            binding.checkBox.isChecked
        )
    }

    private fun onErrorAppearanceFunction(errorCode: Int) {
        if (errorCode == 401) {
            makeIntentToEntranceActivity()
        } else {
            Toast.makeText(
                this.context,
                resources.getString(R.string.review_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun makeIntentToEntranceActivity() {
        startActivity(Intent(activity, EntranceActivity::class.java))
        activity?.overridePendingTransition(0, 0)
        activity?.finish()
    }


}