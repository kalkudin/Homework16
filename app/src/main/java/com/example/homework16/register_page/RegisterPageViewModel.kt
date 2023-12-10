package com.example.homework16.register_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework16.client.ApiClient
import com.example.homework16.model.RegistrationData
import com.example.homework16.model.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterPageViewModel : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val registrationState: StateFlow<RegistrationState> get() = _registrationState

    private val apiService = ApiClient.apiService

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.registerUser(RegistrationData(email, password))
                val responseBody = response.body()

                Log.d("RegisterPageViewModel", "Raw Response Body: ${responseBody?.error}")

                if (responseBody == null) {
                    _registrationState.value = RegistrationState.Error("Null response body")
                    return@launch
                }

                if (responseBody.error != null) {
                    when (responseBody.error) {
                        "Missing password" -> {
                            _registrationState.value = RegistrationState.Error("Missing password")
                        }
                        "Note: Only defined users succeed registration" -> {
                            _registrationState.value = RegistrationState.Error("Invalid email")
                        }
                        else -> {
                            _registrationState.value = RegistrationState.Error("Unknown error")
                        }
                    }
                } else {
                    _registrationState.value = RegistrationState.Success
                }
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error("Registration failed")
            }
        }
    }
}
