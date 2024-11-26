package com.example.Eco.data

data class SignupResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)