package com.example.moviecatalogproject.presentation.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivitySignUpBinding
import com.example.moviecatalogproject.presentation.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val FROM_SIGN_IN = "intent from sign in screen"
        const val FROM_LAUNCH = "intent from launch screen"
        const val TO_SIGN_UP = "to sign up activity"
    }

    private val binding by lazy {
        ActivitySignUpBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val string: String? = intent.getStringExtra(TO_SIGN_UP)

        checkEntryIntents(string!!)

        setupButtonOnClickFunctions()
        onFieldsFocusChange()
        setContentView(binding.root)
    }

    private fun checkEntryIntents(string: String) {
        if (string == FROM_SIGN_IN) {
           
//            binding.root.setTransitionDuration(800)
//            binding.root.setTransition(R.id.myTransition)
//            binding.root.transitionToEnd()
        } else {

        }
    }

    private fun setPercentageToGuideline(guideline: Guideline, percentage: Float) {
        val params = guideline.layoutParams as ConstraintLayout.LayoutParams
        params.guidePercent = percentage
    }

    private fun setupButtonOnClickFunctions() {
        onRegistrationButtonClick()
    }

    private fun onRegistrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
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
            binding.signUpButton.isActivated = true
            binding.signUpButton.isClickable = true
            binding.signUpButton.setTextColor(resources.getColor(R.color.bright_white, theme))
        } else {
            binding.signUpButton.isActivated = false
            binding.signUpButton.isClickable = false
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
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
    }
}