package com.example.moviecatalogproject.presentation.sign_in

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
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
        onFieldsFocusChange()
    }

    private fun setupButtonsOnClickFunctions() {
        onRegistrationButtonClick()
        dateButtonTouchListener()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onRegistrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            showAllErrors()
            if (checkFieldsValidity()) {
                // TODO: send request and intent to main activity
            } else {
                changeRegistrationButtonColor()
            }
        }
    }

    private fun checkFieldsValidity(): Boolean {
        val elementsAmount = binding.linearLayout.childCount
        for (i in 0 until elementsAmount) {
            if (binding.linearLayout.getChildAt(i) is TextView) {
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

    private fun showError(errorId: Int, fieldType: String) {
        when (fieldType) {
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
        val state = errorId != ErrorType.OK
        if (editText == binding.dateEditText) {
            changeDateViewMargin(state)
        } else {
            editText.text.clear()
            changeEditTextMargin(editText, state)
        }
    }

    private fun changeEditTextMargin(editText: EditText, state: Boolean) {
        if (!state) {
            val params = editText.layoutParams as MarginLayoutParams
            params.bottomMargin = 0
        } else {
            val params = editText.layoutParams as MarginLayoutParams
            params.bottomMargin = resources.getDimension(R.dimen.edit_texts_margin).toInt()
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


    private fun onFieldsFocusChange() {
        val elementsAmount = binding.linearLayout.childCount
        for (i in 0 until elementsAmount) {
            val view = binding.linearLayout.getChildAt(i)
            if (view is EditText) {
                onEditTextEditorAction(view)
                view.setOnFocusChangeListener { _, _ ->
                    changeRegistrationButtonColor()
                }
            }
        }
        onEditTextEditorAction(binding.dateEditText)
        binding.dateEditText.setOnFocusChangeListener { _, _ ->
            changeRegistrationButtonColor()

        }
        binding.malePicker.onPickerButtonsClick {
            changeRegistrationButtonColor()
        }
    }

    private fun changeRegistrationButtonColor() {
        if (checkFullnessOfFields()) {
            binding.registrationButton.isActivated = true
            binding.registrationButton.setTextColor(resources.getColor(R.color.bright_white, theme))
        } else {
            binding.registrationButton.isActivated = false
            binding.registrationButton.setTextColor(resources.getColor(R.color.accent, theme))
        }
    }

    private fun checkFullnessOfFields(): Boolean {
        val elementsAmount = binding.linearLayout.childCount
        for (i in 0 until elementsAmount) {
            val view = binding.linearLayout.getChildAt(i)
            if (view is EditText) {
                if (view.text.isEmpty()) {
                    return false
                }
            }
        }
        if (binding.dateEditText.text.isEmpty()) {
            return false
        }
        if (!binding.malePicker.checkIsPickerInvolved()) {
            return false
        }

        return true
    }

    private fun onEditTextEditorAction(editText: EditText) {
        editText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText.clearFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
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