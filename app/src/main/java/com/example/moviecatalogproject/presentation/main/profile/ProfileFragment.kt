package com.example.moviecatalogproject.presentation.main.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.FragmentProfileBinding
import com.example.moviecatalogproject.domain.common.model.ErrorType
import com.example.moviecatalogproject.domain.main.profile.model.Profile
import com.example.moviecatalogproject.presentation.common.MyEditText
import com.example.moviecatalogproject.presentation.common.helper.DateConverter
import com.example.moviecatalogproject.presentation.entrance.EntranceActivity
import com.example.moviecatalogproject.presentation.main.profile.model.MyGlideRequestListener
import java.util.*

class ProfileFragment(val changeProgressBarVisibility: (state: Boolean) -> Unit) : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by lazy {
        ProfileFragmentViewModel(activity?.application!!)
    }

    private lateinit var profile: Profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        changeProgressBarVisibility(true)
        getProfileData()
        onObserveProfileLiveData()
    }

    override fun onStop() {
        super.onStop()
        changeProgressBarVisibility(true)
    }

    private fun getProfileData() {
        viewModel.getProfileData(completeOnError = {
            makeIntentToEntranceActivity()
        })
    }

    private fun onObserveProfileLiveData() {
        viewModel.getProfileLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                profile = it
                setProfileData()
                if (profile.avatarLink != null) {
                    loadAvatar(profile.avatarLink!!)
                } else {
                    setDefaultImage()
                }
                changeRegistrationButtonState(checkFullnessOfFields())
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
            changeProgressBarVisibility(true)
            validateFields()
            if (checkFieldsValidity()) {
                putData()
            } else {
                changeRegistrationButtonState(checkFullnessOfFields())
            }
        }
    }

    private fun onLogoutButtonClick() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout(onLogout = {
                makeIntentToEntranceActivity()
            })
        }
    }

    private fun putData() {
        val changedProfile = Profile(
            id = profile.id,
            nickName = profile.nickName,
            email = binding.emailEditText.text.toString(),
            avatarLink = convertAvatarLinkToNormalForm(binding.avatarLinkEditText.text.toString()),
            name = binding.nameEditText.text.toString(),
            birthDate = DateConverter.convertToBackendFormat(binding.dateEditText.text.toString()),
            gender = binding.genderPicker.getCorrectMeaningOfGender()
        )

        viewModel.putProfileData(changedProfile, completeOnError = {
            onErrorAppearanceFunction(it)
        })
        changeProgressBarVisibility(false)
        Toast.makeText(
            requireContext(),
            requireContext().resources.getString(R.string.saved_data_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun convertAvatarLinkToNormalForm(avatarLink: String): String? {
        avatarLink.ifEmpty {
            return null
        }
        return avatarLink
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadAvatar(link: String) {
        changeProgressBarVisibility(true)
        changeRegistrationButtonState(false)
        Glide.with(requireContext())
            .load(link)
            .listener(
                MyGlideRequestListener(
                    onReadyFunction = {
                        changeRegistrationButtonState(true)
                        changeProgressBarVisibility(false)
                    },
                    onErrorFunction = {
                        changeRegistrationButtonState(true)
                        binding.avatarLinkEditText.text?.clear()
                        setDefaultImage()
                    }
                )
            )
            .placeholder(
                resources.getDrawable(
                    R.drawable.default_avatar_image, requireContext().theme
                )
            )
            .error(
                resources.getDrawable(
                    R.drawable.default_user_avatar_image, requireContext().theme
                )
            )
            .into(binding.avatarImageView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDefaultImage() {
        binding.avatarImageView.setImageDrawable(
            resources.getDrawable(
                R.drawable.default_avatar_image, requireContext().theme
            )
        )
        changeProgressBarVisibility(false)
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
        if (!state) {
            editText.text.clear()
        }
    }


    private fun onFieldsFocusChange() {
        for (id in binding.editTextsGroup.referencedIds) {
            val editText = binding.root.findViewById<MyEditText>(id)
            editText.onEditTextEditorAction()
            editText.setOnFocusChangeListener { _, _ ->
                changeRegistrationButtonState(checkFullnessOfFields())
            }
        }
        binding.genderPicker.onPickerButtonsClick {
            changeRegistrationButtonState(checkFullnessOfFields())
        }
        binding.avatarLinkEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                loadAvatar(binding.avatarLinkEditText.text.toString())
            }
        }
    }

    private fun changeRegistrationButtonState(state: Boolean) {
        if (state) {
            binding.saveProfileChangesButton.isEnabled = true
            setProfileChangesButtonTextColor(R.color.bright_white)
        } else {
            binding.saveProfileChangesButton.isEnabled = false
            setProfileChangesButtonTextColor(R.color.accent)
        }
    }

    private fun setProfileChangesButtonTextColor(colorId: Int) {
        binding.saveProfileChangesButton.setTextColor(
            resources.getColor(colorId, requireContext().theme)
        )
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
                changeRegistrationButtonState(checkFullnessOfFields())
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

    private fun onErrorAppearanceFunction(errorCode: Int) {
        if (errorCode == 401) {
            makeIntentToEntranceActivity()
        }
    }

    private fun makeIntentToEntranceActivity() {
        startActivity(Intent(activity, EntranceActivity::class.java))
        activity?.overridePendingTransition(0, 0)
        activity?.finish()
    }

}