package com.example.moviecatalogproject.presentation.entrance.sign_in

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentRegistrationBinding
import com.example.moviecatalogproject.domain.entrance.model.ErrorType
import java.util.*

class RegistrationFragment(private val bottomButtonCallback: (() -> Unit)? = null) : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private lateinit var theme: Theme

    private val viewModel by lazy {
        SignInFragmentViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_registration, container, false)
        theme = activity?.theme!!
        binding = FragmentRegistrationBinding.bind(mainView)

        onFieldsFocusChange()
        setupButtonsOnClickFunctions()

        return mainView
    }

    override fun onStart() {
        super.onStart()
        changeRegistrationButtonState()
    }

    private fun setupButtonsOnClickFunctions() {
        onRegistrationButtonClick()
        dateButtonTouchListener()
        onSignUpButtonClick()
    }


    private fun onRegistrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            validateFields()
            if (checkFieldsValidity()) {
                // TODO: send request and intent to main activity
            } else {
                changeRegistrationButtonState()
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

    private fun validateFields() {
        validateLoginEditText()
        validateEmailEditText()
        validateNameEditText()
        validatePasswordEditText()
        validateRepeatPasswordEditText()
        validateDateEditText()
        validateMalePicker()
    }

    private fun validateMalePicker() {
        if (binding.malePicker.checkIsPickerInvolved()) {
            prepareTextView(binding.malePickerErrorTextView, ErrorType.OK)
        } else {
            prepareTextView(binding.malePickerErrorTextView, ErrorType.PICKER_ERROR)
        }
    }

    private fun validateLoginEditText() {
        val string = binding.loginEditText.text.toString()
        viewModel.getLoginErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.loginEditText, binding.loginErrorTextView, it)
        }
    }

    private fun validateNameEditText() {
        val string = binding.nameEditText.text.toString()
        viewModel.getNameErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.nameEditText, binding.nameErrorTextView, it)
        }
    }

    private fun validateDateEditText() {
        val string = binding.dateEditText.text.toString()
        viewModel.getDateErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.dateEditText, binding.dateErrorTextView, it)
        }
    }

    private fun validateEmailEditText() {
        val string = binding.emailEditText.text.toString()
        viewModel.getEmailErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.emailEditText, binding.emailErrorTextView, it)
        }
    }

    private fun validatePasswordEditText() {
        val string = binding.passwordEditText.text.toString()
        viewModel.getPasswordErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.passwordEditText, binding.firstPasswordErrorTextView, it)
        }
    }

    private fun validateRepeatPasswordEditText() {
        val string =
            binding.passwordEditText.text.toString() + "\n" + binding.repeatPasswordEditText.text.toString()
        viewModel.getPasswordErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(
                binding.repeatPasswordEditText,
                binding.secondPasswordErrorTextView,
                it
            )
        }
    }

    private fun prepareTextFields(editText: EditText, textView: TextView, errorId: Int) {
        prepareTextView(textView, errorId)
        prepareEditText(editText, errorId)
    }

    private fun prepareTextView(textView: TextView, errorId: Int) {
        if (errorId != ErrorType.OK) {
            textView.text = resources.getString(errorId)
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }

    private fun prepareEditText(editText: EditText, errorId: Int) {
        val state = errorId == ErrorType.OK
        if (editText == binding.dateEditText) {
            if (!state) {
                binding.dateEditText.text!!.clear()
            }
            changeViewBottomMargin(binding.dateView, state)
        } else {
            if (!state) {
                editText.text.clear()
            }
            changeViewBottomMargin(editText, state)
        }
    }

    private fun changeViewBottomMargin(view: View, state: Boolean) {
        if (!state) {
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = 0
        } else {
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = resources.getDimension(R.dimen.edit_texts_margin).toInt()
        }
    }


    private fun onFieldsFocusChange() {
        val elementsAmount = binding.linearLayout.childCount
        for (i in 0 until elementsAmount) {
            val view = binding.linearLayout.getChildAt(i)
            if (view is EditText) {
                onEditTextEditorAction(view)
                view.setOnFocusChangeListener { _, _ ->
                    changeRegistrationButtonState()
                }
            }
        }
        onEditTextEditorAction(binding.dateEditText)
        binding.dateEditText.setOnFocusChangeListener { _, _ ->
            changeRegistrationButtonState()

        }
        binding.malePicker.onPickerButtonsClick {
            changeRegistrationButtonState()
        }
    }

    private fun changeRegistrationButtonState() {
        if (checkFullnessOfFields()) {
            binding.registrationButton.isEnabled = true
            binding.registrationButton.setTextColor(resources.getColor(R.color.bright_white, theme))
        } else {
            binding.registrationButton.isEnabled = false
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
        if (binding.dateEditText.text!!.isEmpty()) {
            return false
        }
        if (!binding.malePicker.checkIsPickerInvolved()) {
            return false
        }

        return true
    }

    private fun onEditTextEditorAction(editText: EditText) {
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText.clearFocus()
                val imm =
                    activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
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
            requireContext(),
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


    private fun onSignUpButtonClick() {
        binding.signUpButton.setOnClickListener {
            bottomButtonCallback?.invoke()
        }
    }
}