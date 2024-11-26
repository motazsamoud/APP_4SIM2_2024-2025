package com.example.Eco.view.LoginActivite

// SignupViewModelFactory.kt


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.retrofit.RetrofitServiceInterface
import com.example.Eco.viewmodel.SignupViewModel

class SignupViewModelFactory(
    private val retrofitService: RetrofitServiceInterface
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(retrofitService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
