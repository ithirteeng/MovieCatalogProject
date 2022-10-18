package com.example.moviecatalogproject.presentation.launch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.presentation.sign_up.SignUpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            makeIntent()

        }

    }

    private fun makeIntent() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }
}