package com.example.moviecatalogproject.presentation.entrance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivityEntranceBinding
import com.example.moviecatalogproject.presentation.entrance.authorization.AuthorizationFragment
import com.example.moviecatalogproject.presentation.entrance.registration.RegistrationFragment

class EntranceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEntranceBinding.inflate(this.layoutInflater)
    }

    private val registrationFragment by lazy {
        RegistrationFragment(
            bottomButtonCallback = {
                replaceSignUpFragment()
            }
        )
    }
    private val authorizationFragment by lazy {
        AuthorizationFragment(
            bottomButtonCallback = {
                replaceSignInFragment()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceSignUpFragment()
    }

    private fun replaceSignInFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, registrationFragment)

            binding.motionLayout.transitionToState(R.id.collapsed)
        }
    }

    private fun replaceSignUpFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, authorizationFragment)

            binding.motionLayout.transitionToState(R.id.expanded)
        }
    }

}