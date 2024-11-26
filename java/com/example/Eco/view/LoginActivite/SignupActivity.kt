package com.example.Eco.view.LoginActivite

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.Eco.R
import com.example.Eco.retrofit.RetrofitInstance
import com.example.Eco.retrofit.RetrofitServiceInterface
import com.example.Eco.viewmodel.SignupViewModel

class SignupActivity : AppCompatActivity() {

    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory(RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signupButton = findViewById<ImageView>(R.id.imageView4)
        signupButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextTextName).text.toString()
            val email = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

            // Appel à la fonction signup du ViewModel
            signupViewModel.signup(name, email, password)
        }

        // Observer la réponse d'inscription
        signupViewModel.signupResponse.observe(this, Observer { response ->
            Toast.makeText(this, "Inscription réussie avec ID: ${response.userId}", Toast.LENGTH_SHORT).show()
        })

        // Observer les messages d'erreur
        signupViewModel.errorMessage.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }
}
