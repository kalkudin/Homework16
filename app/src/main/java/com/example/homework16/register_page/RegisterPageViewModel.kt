package com.example.homework16.register_page

import androidx.lifecycle.ViewModel
import com.example.homework16.client.ApiClient
import com.example.homework16.model.RegistrationData
import com.example.homework16.model.RegistrationResponse
import com.example.homework16.model.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

class RegisterPageViewModel : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val registrationState: StateFlow<RegistrationState> get() = _registrationState

    private val apiService = ApiClient.apiService

    suspend fun registerUser(email: String, password: String) {
        // this is horrible, for starters when you rewrite this, put all of this in an if(isSuccesful) and then run the try-catch
        try {
            val response: Response<RegistrationResponse> = apiService.registerUser(RegistrationData(email, password))

            if (!response.isSuccessful) {
                _registrationState.value = RegistrationState.Error("Registration failed: ${response.message()}")
                return
            }

            response.body()?.let { responseBody ->
                if (responseBody.error != null) {
                    handleRegistrationError(responseBody.error)
                } else {
                    _registrationState.value = RegistrationState.Success
                }
            } ?: run {
                _registrationState.value = RegistrationState.Error("Null response body")
            }
        } catch (e: Exception) {
            _registrationState.value = RegistrationState.Error("Registration failed: ${e.message}")
        }
    }

    //the body of the error is always null, always displays the same message. ask about it later
    private fun handleRegistrationError(error: String) {
        when (error) {
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
    }
}
