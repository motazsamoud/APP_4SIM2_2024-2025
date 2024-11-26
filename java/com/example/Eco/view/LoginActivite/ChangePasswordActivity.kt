package com.example.Eco.view.LoginActivite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.databinding.ActivityChangepasswordBinding
import com.example.Eco.retrofit.RetrofitInstance
import com.example.Eco.retrofit.RetrofitServiceInterface
import com.example.Eco.viewmodel.ChangePasswordViewModel
import com.example.Eco.view.LoginActivite.ChangePasswordViewModelFactory

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangepasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangepasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialiser SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Initialisation du service Retrofit et ViewModel
        val retrofitService = RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java)
        viewModel = ViewModelProvider(this, ChangePasswordViewModelFactory(retrofitService)).get(ChangePasswordViewModel::class.java)

        initViewModel()

        // Listener pour l'imageView pour changer le mot de passe
        binding.imageView4.setOnClickListener {
            handleChangePassword()
        }
    }

    private fun initViewModel() {
        viewModel.changePasswordResponse.observe(this, { success ->
            if (success) {
                Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_LONG).show()
                finish() // Fermer l'activité après succès
            }
        })

        viewModel.errorMessage.observe(this, { error ->
            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun handleChangePassword() {
        val oldPassword = binding.editTextTextPassword.text.toString().trim()
        val newPassword = binding.editTextTextName.text.toString().trim()
        val token = sharedPreferences.getString("accessToken", null)

        if (!oldPassword.isNullOrEmpty() && !newPassword.isNullOrEmpty() && !token.isNullOrEmpty()) {
            val bearerToken = "Bearer $token"  // Ajouter "Bearer " avant le token

            // Afficher le token complet avec "Bearer " dans un Toast pour vérification
            Toast.makeText(this, "AccessToken: $bearerToken ", Toast.LENGTH_LONG).show()

            // Passer le jeton complet avec "Bearer " au ViewModel
            viewModel.changePassword(oldPassword, newPassword, token)
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

}
