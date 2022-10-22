package com.example.moviecatalogproject.presentation.entrance.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.GenderPickerLayoutBinding

class GenderPickerCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val pickerView: View =
        LayoutInflater.from(context).inflate(R.layout.gender_picker_layout, this)
    private val binding = GenderPickerLayoutBinding.bind(pickerView)

    private val buttonsChecker = PickerState()

    fun checkIsPickerInvolved(): Boolean {
        return isMaleButtonChosen() || isFemaleButtonChosen()
    }

    private fun isMaleButtonChosen(): Boolean = buttonsChecker.getMaleState()

    private fun isFemaleButtonChosen(): Boolean = buttonsChecker.getFemaleState()

    fun onPickerButtonsClick(changeListener: () -> Unit) {
        onMaleButtonClick(changeListener)
        onFemaleButtonClick(changeListener)
    }

    fun getCorrectMeaningOfGender(): Int {
        return if (isMaleButtonChosen()) {
            1
        } else {
            0
        }
    }

    private fun onMaleButtonClick(changeListener: () -> Unit) {
        binding.maleButton.setOnClickListener {
            if (buttonsChecker.getFemaleState()) {
                buttonsChecker.changeFemaleState()
                changeFemaleButtonBackground(buttonsChecker.getFemaleState())
            }
            buttonsChecker.changeMaleState()
            changeMaleButtonBackground(buttonsChecker.getMaleState())
            changeListener()
        }
    }

    private fun onFemaleButtonClick(changeListener: () -> Unit) {
        binding.femaleButton.setOnClickListener {
            if (buttonsChecker.getMaleState()) {
                buttonsChecker.changeMaleState()
                changeMaleButtonBackground(buttonsChecker.getMaleState())
            }
            buttonsChecker.changeFemaleState()
            changeFemaleButtonBackground(buttonsChecker.getFemaleState())
            changeListener()

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