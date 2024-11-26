package com.example.Eco.view.ratingActivite

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.R
import com.example.Eco.data.ActiviteRating
import com.example.Eco.viewmodel.RatingActiviteViewModel
import com.example.Eco.databinding.RatingActiviteBinding // Assurez-vous d'importer votre fichier de liaison
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.zxing.BarcodeFormat


class AddRatingActivite : AppCompatActivity() {

    private lateinit var viewModel: RatingActiviteViewModel
    private var selectedRating: Float = 2f
    private var activiteId: String = ""
    private lateinit var binding: RatingActiviteBinding // Déclarez votre objet de liaison

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisez la liaison de la mise en page
        binding = RatingActiviteBinding.inflate(layoutInflater)
        setContentView(binding.root) // Utilisez la vue de liaison

        initViewModel()
        activiteId = intent.getStringExtra("activiteId") ?: ""

        // Configurez le RatingBar
        binding.rbRatingBar.rating = selectedRating
        binding.rbRatingBar.stepSize = 1f

        binding.rbRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
            selectedRating = rating
        }

        binding.ratingButton.setOnClickListener {
            addRating()
        }
    }

    private fun addRating() {
        val commentText = binding.comment.text.toString()

        // Créer un nouvel objet ActiviteRating
        val activite = ActiviteRating(
            activite = activiteId,
            rating = selectedRating,
            comment = commentText,
            user = "26f9c2613c922b783e8d6b2c"
        )

        viewModel.createNewRating(activite)
        generateQRCode(activite) // Générez le QR code après la soumission
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RatingActiviteViewModel::class.java)
        viewModel.getCreateRatingObserver().observe(this, Observer<ActiviteRating?> { rating ->
            if (rating == null) {
                Toast.makeText(this@AddRatingActivite, "Failed to update rating", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddRatingActivite, "Successfully updated rating", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun generateQRCode(data: ActiviteRating) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            // Créer une chaîne contenant les informations nécessaires
            val qrData = "Activité ID: ${data.activite}, Rating: ${data.rating}, Comment: ${data.comment}"
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 400, 400)
            binding.ivQrCode.setImageBitmap(bitmap) // Afficher le QR code
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show()
        }
    }
    private fun scanQRCode(qrData: String) {
        // Extraire l'ID de l'activité et la note/commentaire du QR code
        val parts = qrData.split(", ")
        val activiteId = parts[0].substringAfter("Activité ID: ")
        val rating = parts[1].substringAfter("Rating: ").toFloat()
        val comment = parts[2].substringAfter("Comment: ")

        // Mettre à jour l'interface avec les informations
        binding.comment.setText(comment)
        binding.rbRatingBar.rating = rating

        // Appeler le ViewModel pour récupérer les détails de l'activité
        viewModel.getActiviteDetails(activiteId)
    }

}
