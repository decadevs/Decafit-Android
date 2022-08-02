package com.decagon.decafit.common.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.common.SignUpRequest
import com.decagon.decafit.common.utils.Validation
import com.decagon.decafit.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var fullName: String
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            signUpButton.setOnClickListener {

                fullName = fullNameTextInput.text.toString().trim()
                phoneNumber = phoneNumberTextInput.text.toString().trim()
                email = emailTextInput.text.toString().trim()
                password = passwordTextInput.text.toString().trim()

                // create a user account
                isAllFieldsValidated(SignUpRequest(email, fullName, phoneNumber, password))
            }

            fullNameTextInput.addTextChangedListener {
                fullName = binding.fullNameTextInput.text.toString().trim()
                fullNameTExtInputValidation(fullName)
            }
            phoneNumberTextInput.addTextChangedListener {
                phoneNumber = binding.phoneNumberTextInput.text.toString().trim()
                onNumberTextInputChangeListener(phoneNumber)
            }

            emailTextInput.addTextChangedListener {
                email = binding.emailTextInput.text.toString().trim()
                onEmailTextInputChangeListener(email)
            }
            passwordTextInput.addTextChangedListener {
                password = binding.passwordTextInput.text.toString()
                passwordInputValidationListener(password)
            }
        }
    }

    private fun passwordInputValidationListener(password: String) {
        val validatePassword = Validation.validatePasswordPattern(password)
    }

    private fun onEmailTextInputChangeListener(email: String) {
        if (!Validation.validateEmailInput(email)) {
            binding.emailTextInput.error
            binding.wrongEmailWorningTv.visibility = View.VISIBLE
            binding.wrongEmailWorningTv.setTextColor(
                ContextCompat.getColor(requireContext(),R.color.red)
            )
        } else {
            binding.wrongEmailWorningTv.visibility = View.GONE
        }
    }

    private fun onNumberTextInputChangeListener(phoneNumber: String) {
        if (Validation.validatePhoneNumber(phoneNumber)) {
            binding.wrongEmailWorningTv.visibility = View.GONE
            binding.phoneNumberTextInput.error = "Incomplete number"
        }
    }

    private fun fullNameTExtInputValidation(fullName: String) {
        val errorsList = listOf(
            "Can't start with numbers",
            "must not contain special characters")
        val result = Validation.validateFullNameInput(fullName)
        for (error in result){
            if (errorsList.contains(error))
                binding.fullNameTextInput.error = error
        }

    }

    private fun isAllFieldsValidated(accountData: SignUpRequest){
        val errors = Validation.validateAccountData(accountData)
        with(binding) {
            when {
                errors.contains("Enter your full name") -> fullNameTextInput.error = "Enter your full name"
                errors.contains("cant be empty") -> emailTextInput.error
                errors.contains("Incomplete number") -> phoneNumberTextInput.error = "Incomplete number"
                else -> findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }

}