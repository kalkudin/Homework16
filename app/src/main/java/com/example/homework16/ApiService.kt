package com.example.homework16

import com.example.homework16.model.LoginData
import com.example.homework16.model.RegistrationData
import com.example.homework16.model.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body registrationData: RegistrationData): Response<RegistrationResponse>

    @POST("login")
    suspend fun loginUser(@Body loginData: LoginData): Response<Unit>
}