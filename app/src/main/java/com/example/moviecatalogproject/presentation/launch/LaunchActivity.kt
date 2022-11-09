package com.example.moviecatalogproject.presentation.launch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalogproject.data.ConnectionObserver
import com.example.moviecatalogproject.databinding.ActivityLaunchBinding
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {

    private val viewModel by lazy {
        LaunchActivityViewModel(application)
    }

    private val binding by lazy {
        ActivityLaunchBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        ConnectionObserver.observeConnection(
            context = this,
            onConnectionLost = {
                Log.d("CONNECTIONTIP", "lost")
            },
            onConnectionAvailable = {
                Log.d("CONNECTIONTIP", "available")
                lifecycleScope.launch {
                    makeIntent()
                    delay(1500)
                    binding.progressBar.visibility = View.VISIBLE
                }


            }
        )


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