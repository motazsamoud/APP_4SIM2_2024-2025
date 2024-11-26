package com.example.Eco.data

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
