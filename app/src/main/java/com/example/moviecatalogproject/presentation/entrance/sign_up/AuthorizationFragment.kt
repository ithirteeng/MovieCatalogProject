package com.example.moviecatalogproject.presentation.entrance.sign_up

import android.content.res.Resources
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
import com.example.moviecatalogproject.databinding.FragmentAuthorizationBinding


class AuthorizationFragment(private val bottomButtonCallback: (() -> Unit)) : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding

    private lateinit var theme: Resources.Theme


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_authorization, container, false)
        binding = FragmentAuthorizationBinding.bind(mainView)
        theme = activity?.theme!!


        onFieldsFocusChange()

        binding.registrationButton.setOnClickListener {
            bottomButtonCallback.invoke()
        }

        return mainView
    }

    override fun onStart() {
        super.onStart()
        changeRegistrationButtonState()
    }

    private fun onFieldsFocusChange() {
        onEditTextEditorAction(binding.loginEditText)
        binding.loginEditText.setOnFocusChangeListener { _, _ ->
            changeRegistrationButtonState()
        }
        onEditTextEditorAction(binding.passwordEditText)
        binding.passwordEditText.setOnFocusChangeListener { _, _ ->
            changeRegistrationButtonState()
        }
    }


    private fun changeRegistrationButtonState() {
        if (checkFullnessOfFields()) {
            binding.signUpButton.isEnabled = true
            binding.signUpButton.setTextColor(resources.getColor(R.color.bright_white, theme))
        } else {
            binding.signUpButton.isEnabled = false
            binding.signUpButton.setTextColor(resources.getColor(R.color.accent, theme))
        }
    }

    private fun checkFullnessOfFields(): Boolean {
        if (binding.loginEditText.text!!.isEmpty()) {
            return false
        }
        if (binding.passwordEditText.text!!.isEmpty()) {
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

}