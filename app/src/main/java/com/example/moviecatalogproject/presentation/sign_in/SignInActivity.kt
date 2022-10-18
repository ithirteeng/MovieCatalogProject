package com.example.moviecatalogproject.presentation.sign_in

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignInBinding.inflate(this.layoutInflater)
    }

    private val malePicker by lazy {
        binding.malePicker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        malePicker.onPickerButtonsClick()
    }
}