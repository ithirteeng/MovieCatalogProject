package com.example.moviecatalogproject.presentation.launch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {

    private val viewModel by lazy {
        LaunchActivityViewModel(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.checkTokenExisting()
            delay(1500)
            makeIntent()
        }

    }

    private fun makeIntent() {
        viewModel.getTokenExistingLiveData().observe(this) {
            val intent = if (it) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, EntranceActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

    }
}