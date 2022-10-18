package com.example.moviecatalogproject.presentation.sign_in.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.MalePickerLayoutBinding

class MalePickerCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val pickerView: View =
        LayoutInflater.from(context).inflate(R.layout.male_picker_layout, this)
    private val binding = MalePickerLayoutBinding.bind(pickerView)

    private val buttonsChecker = PickerState()

    fun isMaleButtonChosen(): Boolean = buttonsChecker.getMaleState()

    fun isFemaleButtonChosen(): Boolean = buttonsChecker.getFemaleState()

    fun onPickerButtonsClick() {
        onMaleButtonClick()
        onFemaleButtonClick()
    }

    private fun onMaleButtonClick() {
        binding.maleButton.setOnClickListener {
            if (buttonsChecker.getFemaleState()) {
                buttonsChecker.changeFemaleState()
                changeFemaleButtonBackground(buttonsChecker.getFemaleState())
            }
            buttonsChecker.changeMaleState()
            changeMaleButtonBackground(buttonsChecker.getMaleState())
        }
    }

    private fun onFemaleButtonClick() {
        binding.femaleButton.setOnClickListener {
            if (buttonsChecker.getMaleState()) {
                buttonsChecker.changeMaleState()
                changeMaleButtonBackground(buttonsChecker.getMaleState())

            }
            buttonsChecker.changeFemaleState()
            changeFemaleButtonBackground(buttonsChecker.getFemaleState())
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeFemaleButtonBackground(state: Boolean) {
        if (state) {
            binding.femaleButton.background = resources.getDrawable(
                R.drawable.male_picker_filled_right_part,
                context.theme
            )
        } else {
            binding.femaleButton.background = resources.getDrawable(
                R.drawable.male_picker_empty_right_part,
                context.theme
            )
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeMaleButtonBackground(state: Boolean) {
        if (state) {
            binding.maleButton.background = resources.getDrawable(
                R.drawable.male_picker_filled_left_part,
                context.theme
            )
        } else {
            binding.maleButton.background = resources.getDrawable(
                R.drawable.male_picker_empty_left_part,
                context.theme
            )
        }
    }


}