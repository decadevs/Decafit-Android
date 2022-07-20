package com.decagon.decafit.common.authentication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.common.utils.Validation
import com.decagon.decafit.databinding.FragmentFirstBinding
import com.decagon.decafit.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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

        activateClickListeners()
        loginInputHandler()

    }

    private fun activateClickListeners(){
        binding.fragmentLoginLoginBtn.setOnClickListener {
            val email = binding.fragmentLoginEmailET.text.toString().trim()
            val password = binding.fragmentLoginPasswordET.text.toString().trim()

            if (Validation.validateEmailInput(email)) {
                if (Validation.validatePasswordPattern(password)) {
                    findNavController().navigate(R.id.action_loginFragment_to_SecondFragment)

                } else {
                    // call for incorrect password here
                    Snackbar.make(binding.root, "Invalid Password", Snackbar.LENGTH_LONG).show()
                }
            } else {
                // call for incorrect email here
                Snackbar.make(binding.root, "Invalid email address", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun loginInputHandler(){
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
}