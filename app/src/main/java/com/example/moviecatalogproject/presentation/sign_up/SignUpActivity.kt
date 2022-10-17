package com.example.moviecatalogproject.presentation.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.binding.root)
    }
}