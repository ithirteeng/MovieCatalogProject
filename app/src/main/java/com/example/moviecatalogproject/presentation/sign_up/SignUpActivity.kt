package com.example.moviecatalogproject.presentation.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.databinding.ActivitySignUpBinding
import com.example.moviecatalogproject.presentation.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupButtonOnClickFunctions()
    }

    private fun setupButtonOnClickFunctions() {
        onRegistrationButtonClick()
    }

    private fun onRegistrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }
    }
}