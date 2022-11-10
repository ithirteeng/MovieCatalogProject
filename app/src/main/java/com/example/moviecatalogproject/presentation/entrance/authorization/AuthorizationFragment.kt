package com.example.moviecatalogproject.presentation.entrance.authorization

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentAuthorizationBinding
import com.example.moviecatalogproject.domain.entrance.authorization.model.AuthorizationData
import com.example.moviecatalogproject.presentation.main.MainActivity


class AuthorizationFragment(private val bottomButtonCallback: () -> Unit) : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding

    private val viewModel by lazy {
        AuthorizationFragmentViewModel(activity?.application!!) {
            binding.progressBar.visibility = View.GONE
            binding.registrationButton.isEnabled = true
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.connection_error_repeat_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_authorization, container, false)
        binding = FragmentAuthorizationBinding.bind(mainView)

        onFieldsFocusChange()
        setupButtonOnClickFunctions()
        setEditTextsInputSpaceFilter()

        return mainView
    }

    override fun onStart() {
        super.onStart()
        changeAuthorizationButtonState()
    }

    private fun setupButtonOnClickFunctions() {
        onAuthorizationButtonClick()
        onRegistrationButtonClick()
    }

    private fun onAuthorizationButtonClick() {
        binding.authorizationButton.setOnClickListener {
            postAuthorizationData()
            binding.progressBar.visibility = View.VISIBLE
            observeTokenLiveData()
        }
    }

    private fun postAuthorizationData() {
        binding.registrationButton.isEnabled = false
        viewModel.postAuthorizationData(createAuthorizationData(), completeOnError = {
            binding.loginEditText.text?.clear()
            binding.passwordEditText.text?.clear()
            if (it == 400) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.error_authorization_400),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.registrationButton.isEnabled = true
            binding.progressBar.visibility = View.GONE
            changeAuthorizationButtonState()
        })
    }

    private fun observeTokenLiveData() {
        viewModel.getTokenLiveData().observe(this.viewLifecycleOwner) {
            if (it != null) {
                viewModel.saveTokenToLocalStorage(it)
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun createAuthorizationData(): AuthorizationData {
        return AuthorizationData(
            username = binding.loginEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
    }

    private fun onRegistrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            bottomButtonCallback.invoke()
        }
    }


    private fun onFieldsFocusChange() {
        binding.loginEditText.onEditTextEditorAction()
        binding.loginEditText.setOnFocusChangeListener { _, _ ->
            changeAuthorizationButtonState()
        }
        binding.passwordEditText.onEditTextEditorAction()
        binding.passwordEditText.setOnFocusChangeListener { _, _ ->
            changeAuthorizationButtonState()
        }
    }


    private fun changeAuthorizationButtonState() {
        if (checkFullnessOfFields()) {
            binding.authorizationButton.isEnabled = true
            setAuthorizationButtonTextColor(R.color.bright_white)
        } else {
            binding.authorizationButton.isEnabled = false
            setAuthorizationButtonTextColor(R.color.accent)
        }
    }

    private fun setAuthorizationButtonTextColor(colorId: Int) {
        binding.authorizationButton.setTextColor(
            resources.getColor(
                colorId,
                requireContext().theme
            )
        )
    }

    private fun checkFullnessOfFields(): Boolean {
        if (binding.loginEditText.text!!.isEmpty()) {
            return false
        }
        if (binding.passwordEditText.text!!.isEmpty()) {
            return false
        }

        return true
    }

    private fun setEditTextsInputSpaceFilter() {
        binding.loginEditText.setEditTextsInputSpaceFilter()
        binding.passwordEditText.setEditTextsInputSpaceFilter()
    }

}