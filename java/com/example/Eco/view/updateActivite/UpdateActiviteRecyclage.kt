package com.example.Eco.view.updateActivite

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.Eco.data.ActiviteRecyclage
import com.example.Eco.databinding.UpdateActiviteBinding
import com.example.Eco.viewmodel.ActiviteRecyclageViewModel

class UpdateActiviteRecyclage : AppCompatActivity() {

    private lateinit var viewModel: ActiviteRecyclageViewModel
    private lateinit var binding: UpdateActiviteBinding

    private var activiteId: String = ""
    private var activiteDate: String = ""
    private var activiteMaterial: String = ""
    private var quantity: Int = 0
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisez la liaison de la mise en page
        binding = UpdateActiviteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activiteId = intent.getStringExtra("activiteId") ?: ""
        activiteDate = intent.getStringExtra("activiteDate") ?: ""
        activiteMaterial = intent.getStringExtra("activiteMaterial") ?: ""
        quantity = intent.getIntExtra("quantity", 0)

        // Configurez les champs de texte à l'aide de l'objet de liaison
        binding.etdateAndTime.setText(activiteDate)
        binding.etquantity.setText(quantity.toString())
        binding.etrecyclableMaterial.setText(activiteMaterial)

        initViewModel()

        // Ajouter un bouton pour sélectionner une image
        binding.buttonSelectImage.setOnClickListener {
            selectImage()
        }

        // Créer un événement de mise à jour
        binding.buttonCreate.setOnClickListener {
            updateActivite()
        }

        // Créer un événement de suppression
        binding.buttonDelete.setOnClickListener {
            initViewModelDelete()
        }
    }

    // Méthode pour sélectionner une image dans la galerie
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).into(binding.imageViewSelectedImage)
        }
    }

    private fun updateActivite() {
        val activite = ActiviteRecyclage(
            _id = activiteId,
            dateAndTime = binding.etdateAndTime.text.toString(),
            quantity = Integer.parseInt(binding.etquantity.text.toString()),
            recyclableMaterial = binding.etrecyclableMaterial.text.toString(),
            user = "26f9c2613c922b783e8d6b2c",
            image = imageUri.toString() // Inclure l'URI de l'image sélectionnée
        )
        viewModel.updateActivite(activiteId, activite)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ActiviteRecyclageViewModel::class.java)
        viewModel.getCreateActiviteObserver().observe(this, Observer<ActiviteRecyclage?> {
            if (it == null) {
                Toast.makeText(
                    this@UpdateActiviteRecyclage,
                    "failed to update Activity",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@UpdateActiviteRecyclage,
                    "successfully updated Activity",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun initViewModelDelete() {
        viewModel = ViewModelProvider(this).get(ActiviteRecyclageViewModel::class.java)
        viewModel.getDeleteActiviteObserver().observe(this, Observer<ActiviteRecyclage?> {
            if (it != null) {
                Toast.makeText(this, "Successfully deleted Activity", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error in deleting Activity", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.deleteActivite(activiteId)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}
