package com.example.Eco.view.LoginActivite

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.data.ForgotPasswordRequest
import com.example.Eco.databinding.ActivityLoginBinding
import com.example.Eco.viewmodel.LoginViewModel
import com.example.Eco.retrofit.RetrofitInstance
import com.example.Eco.retrofit.RetrofitServiceInterface
import com.example.Eco.view.activiteList.ActiviteRecyclageList  // Importez l'activité principale
import com.example.Eco.viewmodel.LoginViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var retrofitService: RetrofitServiceInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation du binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisation des préférences partagées
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Initialisation du service Retrofit
        retrofitService = RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java)

        // Initialisation du ViewModel avec Retrofit
        viewModel = ViewModelProvider(this, LoginViewModelFactory(retrofitService)).get(LoginViewModel::class.java)

        // Initialisation des observateurs du ViewModel
        initViewModel()

        // Ajout du clic sur imageView4 pour le login
        binding.imageView4.setOnClickListener {
            handleLogin()
        }

        // Ajout du clic sur "Forgot Password" pour envoyer l'email
        binding.button4.setOnClickListener {
            handleForgotPassword()
        }
    }

    private fun handleForgotPassword() {
        val email = binding.editTextTextPersonName.text.toString().trim()

        // Vérification si l'email est valide
        if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Créer une instance de ForgotPasswordRequest avec l'email saisi
            val forgotPasswordRequest = ForgotPasswordRequest(email)

            // Appeler la méthode Retrofit pour envoyer la requête
            retrofitService.forgotPassword(forgotPasswordRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Si la requête est réussie, afficher un message de confirmation
                        Toast.makeText(this@LoginActivity, "Check your email to reset your password", Toast.LENGTH_SHORT).show()
                    } else {
                        // Si la requête échoue, afficher un message d'erreur
                        Toast.makeText(this@LoginActivity, "Failed to send reset email", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // En cas de problème avec la requête (erreur réseau par exemple)
                    // Afficher un message d'erreur spécifique
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Si l'email n'est pas saisi ou invalide, afficher une alerte
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewModel() {
        // Observer le loginResponse pour la réussite de la connexion
        viewModel.loginResponse.observe(this, { response ->
            // Sauvegarder et afficher le token d'accès
            val accessToken = response.accessToken
            saveAccessToken(accessToken)
            Toast.makeText(this, "Login successful! AccessToken: $accessToken", Toast.LENGTH_LONG).show()

            navigateToMainActivity()
        })

        // Observer errorMessage pour afficher les erreurs
        viewModel.errorMessage.observe(this, { error ->
            Toast.makeText(this, "Login failed: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun handleLogin() {
        val email = binding.editTextTextPersonName.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()

        // Vérification si l'email et le mot de passe sont remplis
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Appeler la fonction de login dans le ViewModel
            viewModel.login(email, password)
        } else {
            // Alerte si l'email ou le mot de passe est vide
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainActivity() {
        // Navigation vers l'activité principale après connexion
        val intent = Intent(this, ActiviteRecyclageList::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveAccessToken(accessToken: String) {
        // Sauvegarde du token dans les préférences partagées
        sharedPreferences.edit().putString("accessToken", accessToken).apply()
        Toast.makeText(this, "Token Saved: $accessToken", Toast.LENGTH_LONG).show()  // Affiche le token brut
    }
}
