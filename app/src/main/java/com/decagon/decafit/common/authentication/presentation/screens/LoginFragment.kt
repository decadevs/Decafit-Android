package com.decagon.decafit.common.authentication.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.common.authentication.data.LoginRequest
import com.decagon.decafit.common.authentication.presentation.viewmodels.AuthViewModels
import com.decagon.decafit.common.common.data.preferences.Preference.initSharedPreference
import com.decagon.decafit.common.common.data.preferences.Preference.saveHeader
import com.decagon.decafit.common.common.data.preferences.Preference.saveName
import com.decagon.decafit.common.utils.Validation
import com.decagon.decafit.common.utils.hideKeyboard
import com.decagon.decafit.common.utils.snackBar
import com.decagon.decafit.databinding.FragmentLoginBinding
import com.decagon.decafit.type.LoginInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModels by viewModels()
    private  lateinit var userInfo: LoginInput

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSharedPreference(requireActivity())

        activateClickListeners()
        loginInputHandler()
    }

    private fun loginInputHandler() {
        val inputHandler :TextWatcher = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userLoginEmail: String = binding.fragmentLoginEmailET.text.toString().trim()
                val userLoginPassword: String = binding.fragmentLoginPasswordET.text.toString().trim()
                binding.fragmentLoginLoginBtn.isEnabled = Validation.validateEmailInput(userLoginEmail)
                        && userLoginPassword.isNotEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.fragmentLoginEmailET.addTextChangedListener(inputHandler)
        binding.fragmentLoginPasswordET.addTextChangedListener(inputHandler)
    }

    private fun activateClickListeners() {
        binding.layout.setOnClickListener {
            it.hideKeyboard()
        }
        binding.signUpTv.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment2())
        }
        binding.fragmentLoginLoginBtn.setOnClickListener {

            val email = binding.fragmentLoginEmailET.text.toString().trim()
            val password = binding.fragmentLoginPasswordET.text.toString().trim()

            if (Validation.validateEmailInput(email)) {
                if (Validation.isValidPasswordFormat(password)) {
                    userInfo = LoginInput(email, password)
                    loginObserver(userInfo)
//                    viewModel.loginUser(userInfo, requireContext())
                } else {
                    // call for incorrect password here
                    snackBar("Invalid Password")
                }
            } else {
                // call for incorrect email here
                snackBar("Invalid email address")
            }

            binding.facebookLogin.setOnClickListener {
                snackBar("login with facebook")
            }
            binding.googleLogin.setOnClickListener {
                snackBar("login with google")
            }
            binding.appleLogin.setOnClickListener {
                snackBar("login with apple")
            }
        }
    }

    private fun loginObserver(userInfo: LoginInput){
        viewModel.loginUser(userInfo, requireContext())
        viewModel.loginResponse.observe(viewLifecycleOwner){
            if (it.data != null){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashBoardFragment())
                it.data!!.userLogin.token?.let { it1 -> saveHeader(it1) }
                saveName(it.data!!.userLogin.fullName)
                snackBar(it.data!!.userLogin.message) //snackBar(it.data!!.login.message)
            }
            if (it.hasErrors()){
                snackBar(it?.errors?.get(0)?.message!!)
            }
        }
    }
}