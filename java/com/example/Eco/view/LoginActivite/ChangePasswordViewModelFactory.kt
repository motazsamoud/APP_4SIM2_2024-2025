// ChangePasswordViewModelFactory.kt
package com.example.Eco.view.LoginActivite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.retrofit.RetrofitServiceInterface
import com.example.Eco.viewmodel.ChangePasswordViewModel

class ChangePasswordViewModelFactory(
    private val retrofitService: RetrofitServiceInterface
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChangePasswordViewModel(retrofitService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
