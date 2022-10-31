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


class AuthorizationFragment(private val bottomButtonCallback: (() -> Unit)) : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding

    private val viewModel by lazy {
        AuthorizationFragmentViewModel(activity?.application!!)
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
        changeRegistrationButtonState()
    }

    private fun setupButtonOnClickFunctions() {
        onSignUpButtonClick()
        onRegistrationButtonClick()
    }

    private fun onSignUpButtonClick() {
        binding.signUpButton.setOnClickListener {
            postAuthorizationData()
            binding.progressBar.visibility = View.VISIBLE
            observeTokenLiveData()
        }
    }

    private fun postAuthorizationData() {
        viewModel.postAuthorizationData(createAuthorizationData(), completeOnError = {
            makeToast(it)
            binding.loginEditText.text?.clear()
            binding.passwordEditText.text?.clear()
            binding.progressBar.visibility = View.GONE
            changeRegistrationButtonState()
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
            changeRegistrationButtonState()
        }
        binding.passwordEditText.onEditTextEditorAction()
        binding.passwordEditText.setOnFocusChangeListener { _, _ ->
            changeRegistrationButtonState()
        }
    }


    private fun changeRegistrationButtonState() {
        if (checkFullnessOfFields()) {
            binding.signUpButton.isEnabled = true
            setSignUpButtonTextColor(R.color.bright_white)
        } else {
            binding.signUpButton.isEnabled = false
            setSignUpButtonTextColor(R.color.accent)
        }
    }

    private fun setSignUpButtonTextColor(colorId: Int) {
        binding.signUpButton.setTextColor(resources.getColor(colorId, requireContext().theme))
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

    private fun makeToast(stringId: Int) {
        Toast.makeText(requireContext(), resources.getString(stringId), Toast.LENGTH_SHORT).show()
    }

}