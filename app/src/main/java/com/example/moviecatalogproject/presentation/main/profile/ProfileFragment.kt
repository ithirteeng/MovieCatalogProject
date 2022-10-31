package com.example.moviecatalogproject.presentation.main.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentProfileBinding
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.domain.model.ErrorType
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.helper.DateConverter
import java.util.*

class ProfileFragment(val onFragmentStart: () -> Unit) : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by lazy {
        ProfileFragmentViewModel(activity?.application!!)
    }

    private lateinit var profile: Profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainView = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(mainView)

        setEditTextsInputSpaceFilter()
        setupButtonOnClickFunctions()
        onFieldsFocusChange()

        return mainView
    }

    override fun onStart() {
        super.onStart()
        onFragmentStart()
        binding.progressBar.visibility = View.VISIBLE
        getProfileData()
        onObserveProfileLiveData()
    }

    private fun getProfileData() {
        viewModel.getProfileData(completeOnError = {
            startActivity(Intent(activity, EntranceActivity::class.java))
            activity?.finish()
        })
    }

    private fun onObserveProfileLiveData() {
        viewModel.getProfileLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                profile = it
                setProfileData()
                changeRegistrationButtonState()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setProfileData() {
        binding.emailEditText.setText(profile.email)
        binding.nameEditText.setText(profile.name)
        binding.avatarLinkEditText.setText(profile.avatarLink)
        binding.dateEditText.setText(DateConverter.convertToNormalForm(profile.birthDate))
        binding.usernameTextView.text = profile.nickName
        binding.genderPicker.setCorrectGender(profile.gender)

    }

    private fun setupButtonOnClickFunctions() {
        dateButtonTouchListener()
        onSaveButtonClick()
        onLogoutButtonClick()
    }

    private fun onSaveButtonClick() {
        binding.saveProfileChangesButton.setOnClickListener {
            validateFields()
            if (checkFieldsValidity()) {
                putData()
            } else {
                changeRegistrationButtonState()
            }
        }
    }

    private fun onLogoutButtonClick() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout(onLogout = {
                startActivity(Intent(activity, EntranceActivity::class.java))
                activity?.finish()
            })
        }
    }

    private fun putData() {
        val changedProfile = Profile(
            id = profile.id,
            nickName = profile.nickName,
            email = binding.emailEditText.text.toString(),
            avatarLink = binding.avatarLinkEditText.text.toString(),
            name = binding.nameEditText.text.toString(),
            birthDate = DateConverter.convertToBackendFormat(binding.dateEditText.text.toString()),
            gender = binding.genderPicker.getCorrectMeaningOfGender()
        )
        viewModel.putProfileData(changedProfile, completeOnError = {
            if (it == 401) {
                startActivity(Intent(activity, EntranceActivity::class.java))
                activity?.finish()
            }
        })
        Toast.makeText(
            requireContext(),
            requireContext().resources.getString(R.string.saved_data_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkFieldsValidity(): Boolean {
        for (id in binding.errorTextViewsGroup.referencedIds) {
            val textView = binding.root.findViewById<TextView>(id)
            if (textView.visibility != View.GONE) {
                return false
            }
        }
        return true
    }

    private fun validateFields() {
        validateDateEditText()
        validateEmailEditText()
    }

    private fun validateDateEditText() {
        val string = binding.dateEditText.text.toString()
        viewModel.getDateErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.dateEditText, binding.dateErrorTextView, it)
        }
    }

    private fun validateEmailEditText() {
        val string = binding.emailEditText.text.toString()
        viewModel.getEmailErrorLiveData(string).observe(this.viewLifecycleOwner) {
            prepareTextFields(binding.emailEditText, binding.emailErrorTextView, it)
        }
    }

    private fun prepareTextFields(editText: EditText, textView: TextView, errorId: Int) {
        prepareTextView(textView, errorId)
        prepareEditText(editText, errorId)
    }

    private fun prepareTextView(textView: TextView, errorId: Int) {
        if (errorId != ErrorType.OK) {
            textView.text = resources.getString(errorId)
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }

    private fun prepareEditText(editText: EditText, errorId: Int) {
        val state = errorId == ErrorType.OK
        if (editText == binding.dateEditText) {
            if (!state) {
                binding.dateEditText.text!!.clear()
            }
        } else {
            if (!state) {
                editText.text.clear()
            }
        }
    }


    private fun onFieldsFocusChange() {
        for (id in binding.editTextsGroup.referencedIds) {
            val editText = binding.root.findViewById<EditText>(id)
            onEditTextEditorAction(editText)
            editText.setOnFocusChangeListener { _, _ ->
                changeRegistrationButtonState()
            }
        }
        binding.genderPicker.onPickerButtonsClick {
            changeRegistrationButtonState()
        }
    }

    private fun changeRegistrationButtonState() {
        if (checkFullnessOfFields()) {
            binding.saveProfileChangesButton.isEnabled = true
            binding.saveProfileChangesButton.setTextColor(
                resources.getColor(
                    R.color.bright_white,
                    requireContext().theme
                )
            )
        } else {
            binding.saveProfileChangesButton.isEnabled = false
            binding.saveProfileChangesButton.setTextColor(
                resources.getColor(
                    R.color.accent,
                    requireContext().theme
                )
            )
        }
    }

    private fun checkFullnessOfFields(): Boolean {
        for (id in binding.editTextsGroup.referencedIds) {
            val editText = binding.root.findViewById<EditText>(id)
            if (editText.text.isEmpty() && editText != binding.avatarLinkEditText) {
                return false
            }
        }
        if (!binding.genderPicker.checkIsPickerInvolved()) {
            return false
        }

        return true
    }

    private fun onEditTextEditorAction(editText: EditText) {
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText.clearFocus()
                val imm =
                    activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun dateButtonTouchListener() {
        binding.dateButton.setOnClickListener {
            pickDate()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pickDate() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            R.style.date_picker_style,
            { _, year, month, day ->
                var dayString = day.toString()
                var monthString = month.toString()
                if (day < 10) {
                    dayString = "0$day"
                }
                if (month < 9) {
                    monthString = "0${month + 1}"
                }
                if (month == 9) {
                    monthString = "10"
                }
                binding.dateEditText.setText("$dayString.$monthString.$year")
                changeRegistrationButtonState()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setEditTextsInputSpaceFilter() {
        binding.avatarLinkEditText.setEditTextsInputSpaceFilter()
        binding.dateEditText.setEditTextsInputSpaceFilter()
        binding.emailEditText.setEditTextsInputSpaceFilter()
        binding.nameEditText.setEditTextsInputSpaceFilter()


    }
}