package com.example.homework16.register_page

import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homework16.base_fragment.BaseFragment
import com.example.homework16.databinding.FragmentRegisterPageBinding
import com.example.homework16.model.RegistrationState
import kotlinx.coroutines.launch

class RegisterPageFragment :
    BaseFragment<FragmentRegisterPageBinding>(FragmentRegisterPageBinding::inflate) {

    private val viewModel by viewModels<RegisterPageViewModel>()

    override fun setUp() {
        Log.d("RegisterPageFragment", "Setting up fragment")
        setUpRegisterButton()
        observeRegistrationState()
        binding.icShowPassword.setOnClickListener {
            togglePasswordVisibility(binding.etPassword)
        }
    }

    private fun setUpRegisterButton() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.registerUser(email, password)
        }
    }

    private fun observeRegistrationState() {
        lifecycleScope.launch {
            viewModel.registrationState.collect { state ->
                when (state) {
                    is RegistrationState.Initial -> {
                        Log.d("RegistrationState", "Initial state")
                    }

                    is RegistrationState.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("RegistrationState", "Registration successful")
                    }

                    is RegistrationState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        Log.e("RegistrationState", "Error: ${state.message}")
                    }
                }
            }
        }
    }

    private fun togglePasswordVisibility(editText: AppCompatEditText) {
        val isVisible = editText.transformationMethod == null
        editText.transformationMethod = if (isVisible) {
            PasswordTransformationMethod.getInstance()
        } else {
            null
        }
    }
}