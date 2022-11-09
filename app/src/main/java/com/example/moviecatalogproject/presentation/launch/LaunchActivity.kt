package com.example.moviecatalogproject.presentation.launch

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalogproject.databinding.ActivityLaunchBinding
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {

    private val viewModel by lazy {
        LaunchActivityViewModel(application) {
            onInternetConnectionFailure()
        }
    }

    private val binding by lazy {
        ActivityLaunchBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(1000)
            checkTokenExisting()
            binding.progressBar.visibility = View.VISIBLE
        }

        onCheckingTokenExistingComplete()
    }

    private fun checkTokenExisting() {
        viewModel.checkTokenExisting()
    }

    private fun onCheckingTokenExistingComplete() {
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

    private fun onInternetConnectionFailure() {
        binding.launchLogoImageView.visibility = View.GONE
        binding.refreshButton.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.connectionTextView.visibility = View.VISIBLE

        binding.refreshButton.setOnClickListener {
            checkTokenExisting()
            onCheckingTokenExistingComplete()

            binding.refreshButton.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.connectionTextView.visibility = View.GONE
        }
    }

}