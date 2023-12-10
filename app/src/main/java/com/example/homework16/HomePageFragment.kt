package com.example.homework16

import androidx.navigation.fragment.findNavController
import com.example.homework16.base_fragment.BaseFragment
import com.example.homework16.databinding.FragmentHomePageBinding

class HomePageFragment :
    BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    override fun setUp() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setUpRegister()
        setUpLogin()
    }

    private fun setUpRegister() {
        binding.btnToRegister.setOnClickListener() {
            findNavController().navigate(R.id.action_homePageFragment_to_registerPageFragment)
        }
    }

    private fun setUpLogin() {
        binding.btnToLogin.setOnClickListener() {
            findNavController().navigate(R.id.action_homePageFragment_to_loginPageFragment)
        }
    }
}