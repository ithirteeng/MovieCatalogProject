package com.example.moviecatalogproject.presentation.movie_info.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentCustomDialogBinding

class CustomDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_custom_dialog, null)
        binding = FragmentCustomDialogBinding.bind(view)

        onRatingBarChange()


        val builder = AlertDialog.Builder(requireActivity(), R.style.ProgressDialogTheme)
        return builder.setView(view).create()
    }

    private fun onRatingBarChange() {
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            //
        }
    }

}