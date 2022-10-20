package com.example.moviecatalogproject.presentation.entrance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivityEntranceBinding
import com.example.moviecatalogproject.presentation.entrance.sign_in.RegistrationFragment
import com.example.moviecatalogproject.presentation.entrance.sign_up.AuthorizationFragment

class EntranceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEntranceBinding.inflate(this.layoutInflater)
    }

    private val registrationFragment by lazy {
        RegistrationFragment{
            replaceSignUpFragment()
        }
    }
    private val authorizationFragment by lazy {
        AuthorizationFragment {
            replaceSignInFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceSignUpFragment()
        setupButtonsFunctions()
    }


    private fun setupButtonsFunctions() {
        setupAuthorizationFragmentOnClickFunctions()
        setupRegistrationFragmentOnClickFunctions()
    }

    private fun setupRegistrationFragmentOnClickFunctions() {

    }

    private fun setupAuthorizationFragmentOnClickFunctions() {

    }


    private fun replaceSignInFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, registrationFragment)

            binding.root.transitionToState(R.id.collapsed)
        }
    }

    private fun replaceSignUpFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, authorizationFragment)

            binding.root.transitionToState(R.id.expanded)
        }
    }

}