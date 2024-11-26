package com.example.Eco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Eco.data.SignupRequest
import com.example.Eco.data.SignupResponse
import com.example.Eco.retrofit.RetrofitServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val retrofitService: RetrofitServiceInterface) : ViewModel() {

    val signupResponse = MutableLiveData<SignupResponse>()
    val errorMessage = MutableLiveData<String>()

    // Fonction pour effectuer l'inscription
    fun signup(name: String, email: String, password: String) {
        val request = SignupRequest(name, email, password)

        // Appel de l'API d'inscription via Retrofit
        retrofitService.signup(request).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    // Inscription réussie, mise à jour de signupResponse
                    signupResponse.value = response.body()
                } else {
                    // Échec de l'inscription, mise à jour du message d'erreur
                    errorMessage.value = "Signup failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                // En cas d'erreur de réseau ou autre
                errorMessage.value = t.message
            }
        })
    }
}

