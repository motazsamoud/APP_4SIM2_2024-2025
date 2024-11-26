package com.example.Eco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Eco.data.LoginRequest
import com.example.Eco.data.LoginResponse
import com.example.Eco.retrofit.RetrofitServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val retrofitService: RetrofitServiceInterface) : ViewModel() {

    val loginResponse = MutableLiveData<LoginResponse>()
    val errorMessage = MutableLiveData<String>()

    // Fonction pour effectuer la connexion
    fun login(email: String, password: String) {
        val request = LoginRequest(email, password)

        // Appeler l'API via Retrofit
        retrofitService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Si la connexion réussit, assigner le résultat à loginResponse
                    loginResponse.value = response.body()
                } else {
                    // Si la connexion échoue, assigner un message d'erreur à errorMessage
                    errorMessage.value = "Login failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // En cas d'erreur de réseau ou autre, assigner l'erreur à errorMessage
                errorMessage.value = t.message
            }
        })
    }
}
