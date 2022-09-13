package com.decagon.decafit.common.authentication.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.common.authentication.data.SignUpRequest
import com.decagon.decafit.common.authentication.presentation.viewmodels.AuthViewModels
import com.decagon.decafit.common.common.data.preferences.Preference
import com.decagon.decafit.common.utils.ProgressBarLoading
import com.decagon.decafit.common.utils.Validation
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentSignUpBinding
import com.decagon.decafit.type.RegisterInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var fullName: String
    private lateinit var phoneNumber: String
    private  lateinit var userInfo: RegisterInput
    private val viewModel:AuthViewModels by viewModels()



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
        Preference.initSharedPreference(requireActivity())
        networkObsever()
       initSingUpInput()
        isLoading()

    }

    private fun initSingUpInput(){
        with(binding) {
            signUpButton.setOnClickListener {

                fullName = fullNameTextInput.text.toString().trim()
                phoneNumber = phoneNumberTextInput.text.toString().trim()
                email = emailTextInput.text.toString().trim()
                password = passwordTextInput.text.toString().trim()
                // create a user account
                isAllFieldsValidated(SignUpRequest(email, fullName, phoneNumber, password))
            }
            signInTv.setOnClickListener {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
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
        val validatePassword = Validation.isValidPasswordFormat(password)
        if (validatePassword){

        }
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
                errors.contains("cant be empty") -> emailTextInput.error ="incorrect email"
                errors.contains("Incomplete number") -> phoneNumberTextInput.error = "Incomplete number"
                errors.contains("Minimum of 8 characters") -> passwordTextInput.error = "Minimum of 8 characters"
                errors.contains("Uppercase and lowercase")-> passwordTextInput.error = "must contain Uppercase and lowercase"
                errors.contains("Numbers")-> passwordTextInput.error = "must contain Numbers"
                else -> {
                    userInfo= RegisterInput(accountData.fullName, accountData.email,accountData.phone_number,accountData.password)
                    singUpObserver(userInfo)
                }
            }
        }
    }

    private fun isLoading(){
        val progressBar = ProgressBarLoading(requireContext())
        viewModel.progressBar.observe(viewLifecycleOwner){
            if (it){
                progressBar.show()
            }else{
                progressBar.dismiss()
            }
    }}

    private fun singUpObserver(userInfo: RegisterInput){
        viewModel.registerUser( userInfo, requireContext())
        viewModel.registerResponse.observe(viewLifecycleOwner){ resources->
            if(resources.data!=null){
                Toast.makeText(requireContext(), resources.data!!.userRegister.message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            }
            if (resources.hasErrors()){
                snackBar(resources?.errors?.get(0)?.message!!)
            }
        }
    }

    private fun networkObsever(){
        viewModel.networkCheckResponse.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                snackBar(it)
            }
        }
    }

}