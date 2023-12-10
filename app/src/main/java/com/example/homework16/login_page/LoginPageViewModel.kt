package com.example.homework16.login_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework16.ApiService
import com.example.homework16.client.ApiClient
import com.example.homework16.model.LoginData
import com.example.homework16.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginPageViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> get() = _loginState

    private val apiService: ApiService = ApiClient.apiService

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.loginUser(LoginData(email, password))

                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login failed: ${e.message}")
            }
        }
    }
}