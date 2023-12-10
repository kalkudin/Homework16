package com.example.homework16.login_page

import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homework16.base_fragment.BaseFragment
import com.example.homework16.databinding.FragmentLoginPageBinding
import com.example.homework16.model.LoginState
import kotlinx.coroutines.launch

class LoginPageFragment :
    BaseFragment<FragmentLoginPageBinding>(FragmentLoginPageBinding::inflate) {

    private val viewModel by viewModels<LoginPageViewModel>()

    override fun setUp() {
        setUpLoginButton()
        observeLoginState()
        binding.icShowPassword.setOnClickListener {
            togglePasswordVisibility(binding.etPassword)
        }
    }

    private fun setUpLoginButton() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.loginUser(email, password)
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Initial -> {
                    }
                    is LoginState.Success -> {
                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    }
                    is LoginState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
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