// ChangePasswordViewModel.kt
package com.example.Eco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Eco.data.ChangePasswordRequest
import com.example.Eco.retrofit.RetrofitServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel(private val retrofitService: RetrofitServiceInterface) : ViewModel() {

    val changePasswordResponse = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun changePassword(oldPassword: String, newPassword: String, token: String) {
        val request = ChangePasswordRequest(oldPassword, newPassword)
        retrofitService.changePassword("Bearer $token", request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    changePasswordResponse.value = true
                } else {
                    errorMessage.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                errorMessage.value = t.message
            }
        })
    }
}
