package com.example.moviecatalogproject.presentation.sign_in

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignInBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}