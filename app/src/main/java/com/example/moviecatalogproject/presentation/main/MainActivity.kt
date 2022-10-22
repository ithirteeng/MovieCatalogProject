package com.example.moviecatalogproject.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.data.repository.TokenRepositoryImpl
import com.example.moviecatalogproject.databinding.ActivityMainBinding
import com.example.moviecatalogproject.domain.main.model.GetTokenUseCase

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(this.layoutInflater)
    }
    private val tokenRepositoryImpl by lazy {
        TokenRepositoryImpl(applicationContext)
    }

    private val getTokenUseCase by lazy {
        GetTokenUseCase(tokenRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView.text = getTokenUseCase.execute().token
    }
}