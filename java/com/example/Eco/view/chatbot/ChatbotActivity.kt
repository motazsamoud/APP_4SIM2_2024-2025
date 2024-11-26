package com.example.Eco.view.chatbot

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.services.lexrts.model.PostTextRequest
import com.amazonaws.services.lexrts.AmazonLexRuntime
import com.amazonaws.services.lexrts.AmazonLexRuntimeClient
import com.amazonaws.services.lexrts.model.PostTextResult
import com.example.Eco.R
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class ChatbotActivity : AppCompatActivity() {
    private lateinit var chatInput: EditText
    private lateinit var sendButton: Button
    private lateinit var chatOutput: TextView

    private lateinit var lexClient: AmazonLexRuntime
    private var isLexClientInitialized = false // État pour vérifier si lexClient est initialisé

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        chatInput = findViewById(R.id.chatInput)
        sendButton = findViewById(R.id.sendButton)
        chatOutput = findViewById(R.id.chatOutput)

        // Initialisez AWSMobileClient
        AWSMobileClient.getInstance().initialize(this, object : Callback<UserStateDetails> {
            override fun onResult(userStateDetails: UserStateDetails?) {
                // Initialisation réussie, créez le client Lex
                lexClient = AmazonLexRuntimeClient(AWSMobileClient.getInstance())
                isLexClientInitialized = true // Marquez lexClient comme initialisé
                chatOutput.append("Chatbot: Prêt à recevoir des messages.\n")
            }

            override fun onError(e: Exception) {
                // Gérer les erreurs d'initialisation
                chatOutput.append("Erreur d'initialisation: ${e.message}\n")
            }
        })

        sendButton.setOnClickListener {
            val userMessage = chatInput.text.toString()
            if (userMessage.isNotEmpty()) {
                handleChatbotResponse(userMessage)
                chatInput.text.clear() // Efface le champ de saisie
            }
        }
    }

    private fun handleChatbotResponse(userMessage: String) {
        chatOutput.append("Vous: $userMessage\n")

        if (!isLexClientInitialized) {
            chatOutput.append("Chatbot: Lex n'est pas prêt. Réessayez plus tard.\n")
            // Ajoutez ici un mécanisme de retry, par exemple :
            sendButton.isEnabled = false // Désactivez le bouton d'envoi

            // Réessayer après un certain temps (par exemple, 2 secondes)
            Executors.newSingleThreadScheduledExecutor().schedule({
                runOnUiThread {
                    sendButton.isEnabled = true // Réactivez le bouton d'envoi
                }
            }, 2, TimeUnit.SECONDS)
            return // Sortie si lexClient n'est pas encore prêt
        }

        // Utilisation de SharedPreferences pour obtenir ou générer un userId
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null) ?: UUID.randomUUID().toString()

        // Enregistrer l'ID utilisateur pour la prochaine fois
        sharedPreferences.edit().putString("userId", userId).apply()

        // Appeler Amazon Lex pour obtenir une réponse
        val request = PostTextRequest()
            .withBotName("motaz") // Remplacez par le nom de votre bot
            .withBotAlias("TestBotAlias") // Remplacez par l'alias de votre bot
            .withUserId(userId)
            .withInputText(userMessage)

        // Exécuter l'appel Lex dans un thread séparé
        Executors.newSingleThreadExecutor().execute {
            try {
                val response: PostTextResult = lexClient.postText(request)
                runOnUiThread {
                    chatOutput.append("Chatbot: ${response.message}\n")
                }
            } catch (e: Exception) {
                runOnUiThread {
                    chatOutput.append("Chatbot: Désolé, je n'ai pas pu répondre.\n")
                }
            }
        }
    }
}
