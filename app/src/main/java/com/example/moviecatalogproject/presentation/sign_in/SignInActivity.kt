package com.example.moviecatalogproject.presentation.sign_in

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivitySignInBinding
import com.example.moviecatalogproject.domain.sign_in.model.ErrorType
import com.example.moviecatalogproject.domain.sign_in.validator.*
import java.util.*

class SignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignInBinding.inflate(this.layoutInflater)
    }

    private val viewModel by lazy {
        SignInViewActivityModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupButtonsOnClickFunctions()
    }

    private fun setupButtonsOnClickFunctions() {
        binding.malePicker.onPickerButtonsClick()
        onRegistrationButtonClick()
        dateButtonTouchListener()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onRegistrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            showAllErrors()
            if (checkDataValidity()) {
                binding.registrationButton.background = resources.getDrawable(
                    R.drawable.filled_button_background,
                    theme
                )
            }
        }
    }

    private fun checkDataValidity(): Boolean {
        val elementsAmount = binding.linearLayout.childCount
        for (i in 0 until elementsAmount) {
            if (i % 2 != 0) {
                val textView = binding.linearLayout.getChildAt(i) as TextView
                if (textView.visibility != View.GONE) {
                    return false
                }
            }
        }
        return true
    }

    private fun showAllErrors() {
        validateEditText(binding.loginEditText, SignInViewActivityModel.LOGIN)
        validateEditText(binding.nameEditText, SignInViewActivityModel.NAME)
        validateEditText(binding.emailEditText, SignInViewActivityModel.EMAIL)
        validateEditText(binding.passwordEditText, SignInViewActivityModel.PASSWORD_SIZE)
        validateEditText(binding.repeatPasswordEditText, SignInViewActivityModel.PASSWORD_EQUALITY)
        validateEditText(binding.dateEditText, SignInViewActivityModel.DATE)
        validateMalePicker()
    }

    private fun validateMalePicker() {
        if (binding.malePicker.checkIsPickerInvolved()) {
            showError(ErrorType.OK, SignInViewActivityModel.MALE)
        } else {
            showError(ErrorType.PICKER_ERROR, SignInViewActivityModel.MALE)
        }
    }

    private fun validateEditText(editText: EditText, editTextType: String) {
        var string = editText.text.toString()
        if (editTextType == SignInViewActivityModel.PASSWORD_EQUALITY) {
            string += "\n${binding.passwordEditText.text}"
        }
        val it = viewModel.getErrorId(editTextType, string)
        showError(it, editTextType)
    }

    private fun showError(errorId: Int, editTextType: String) {
        when (editTextType) {
            SignInViewActivityModel.NAME -> prepareTextFields(
                binding.nameEditText,
                binding.nameErrorTextView,
                errorId
            )
            SignInViewActivityModel.PASSWORD_SIZE -> prepareTextFields(
                binding.passwordEditText,
                binding.firstPasswordErrorTextView,
                errorId
            )
            SignInViewActivityModel.PASSWORD_EQUALITY -> prepareTextFields(
                binding.repeatPasswordEditText,
                binding.secondPasswordErrorTextView,
                errorId
            )
            SignInViewActivityModel.DATE -> prepareTextFields(
                binding.dateEditText,
                binding.dateErrorTextView,
                errorId
            )
            SignInViewActivityModel.LOGIN -> prepareTextFields(
                binding.loginEditText,
                binding.loginErrorTextView,
                errorId
            )
            SignInViewActivityModel.EMAIL -> prepareTextFields(
                binding.emailEditText,
                binding.emailErrorTextView,
                errorId
            )
            SignInViewActivityModel.MALE -> prepareTextView(
                binding.malePickerErrorTextView,
                errorId
            )
        }

    }

    private fun prepareTextFields(editText: EditText, textView: TextView, errorId: Int) {
        prepareEditText(editText, errorId)
        prepareTextView(textView, errorId)
    }

    private fun prepareEditText(editText: EditText, errorId: Int) {
        if (errorId != ErrorType.OK) {
            if (editText == binding.dateEditText) {
                changeDateViewMargin(false)
            } else {
                editText.text.clear()
                val params = editText.layoutParams as MarginLayoutParams
                params.bottomMargin = 0
            }
        } else {
            if (binding.dateEditText == editText) {
                changeDateViewMargin(true)
            } else {
                val params = editText.layoutParams as MarginLayoutParams
                params.bottomMargin = resources.getDimension(R.dimen.edit_texts_margin).toInt()
            }

        }
    }

    private fun changeDateViewMargin(state: Boolean) {
        if (!state) {
            val params = binding.dateView.layoutParams as MarginLayoutParams
            params.bottomMargin = 0
        } else {
            val params = binding.dateView.layoutParams as MarginLayoutParams
            params.bottomMargin = resources.getDimension(R.dimen.edit_texts_margin).toInt()
        }
    }

    private fun prepareTextView(textView: TextView, errorId: Int) {
        if (errorId != ErrorType.OK) {
            textView.text = resources.getString(errorId)
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }


    }

    private fun dateButtonTouchListener() {
        binding.dateButton.setOnClickListener {
            pickDate()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pickDate() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            R.style.date_picker_style,
            { _, year, month, day ->
                var dayString = day.toString()
                var monthString = month.toString()
                if (day < 10) {
                    dayString = "0$day"
                }
                if (month < 10) {
                    monthString = "0$month"
                }
                binding.dateEditText.setText("$dayString.$monthString.$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}