package com.example.Eco.view.addActivite

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.R
import com.example.Eco.data.ActiviteRecyclage
import com.example.Eco.viewmodel.ActiviteRecyclageViewModel
import com.example.Eco.databinding.AddActiviteBinding // Assurez-vous d'importer votre fichier de liaison
import com.bumptech.glide.Glide // Pour afficher les images
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.example.Eco.ml.SsdMobilenetV11Metadata1
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp

class AddActiviteRecyclage : AppCompatActivity() {

    private lateinit var viewModel: ActiviteRecyclageViewModel
    private lateinit var binding: AddActiviteBinding
    private var selectedImageUri: Uri? = null
    private lateinit var model: SsdMobilenetV11Metadata1 // Instance du modèle
    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(300, 300, ResizeOp.ResizeMethod.BILINEAR))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddActiviteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        model = SsdMobilenetV11Metadata1.newInstance(this) // Initialisation du modèle

        binding.buttonCreate.setOnClickListener {
            addActivite()
        }

        binding.buttonImportImage.setOnClickListener {
            openImageSelector()
        }
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data
                displaySelectedImage()

                selectedImageUri?.let {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                    detectRecyclableMaterial(bitmap) // Détection automatique
                }
            }
        }

    private fun displaySelectedImage() {
        selectedImageUri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.imageViewSelectedImage)
        }
    }

    private fun detectRecyclableMaterial(bitmap: Bitmap) {
        var image = TensorImage.fromBitmap(bitmap)
        image = imageProcessor.process(image)

        val outputs = model.process(image)
        val locations = outputs.locationsAsTensorBuffer.floatArray
        val classes = outputs.classesAsTensorBuffer.floatArray
        val scores = outputs.scoresAsTensorBuffer.floatArray

        val labels = FileUtil.loadLabels(this, "labels.txt")

        // Recherche du matériau avec la meilleure probabilité
        val detectedMaterial = scores.indices
            .filter { scores[it] > 0.5 } // Seuil de confiance
            .maxByOrNull { scores[it] } // Meilleure probabilité
            ?.let { labels[classes[it].toInt()] }

        detectedMaterial?.let {
            binding.etrecyclableMaterial.setText(it) // Renseigner automatiquement le champ
            Toast.makeText(this, "Material detected: $it", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "No recyclable material detected", Toast.LENGTH_SHORT).show()
        }
    }
    // Méthode pour enregistrer l'image dans la mémoire interne
    private fun saveImageToInternalStorage(uri: Uri): String? {
        val bitmap: Bitmap
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        // Créer un fichier dans le répertoire interne de l'application
        val filename = "image_${System.currentTimeMillis()}.png"
        val file = File(filesDir, filename)

        return try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            file.absolutePath // Retourne le chemin du fichier
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun addActivite() {
        val imagePath = selectedImageUri?.let { saveImageToInternalStorage(it) }

        val activite = ActiviteRecyclage(
            dateAndTime = binding.etdateAndTime.text.toString(),
            quantity = Integer.parseInt(binding.etquantity.text.toString()),
            recyclableMaterial = binding.etrecyclableMaterial.text.toString(),
            user = "26f9c2613c922b783e8d6b2c",
            image = imagePath.toString()
        )
        viewModel.createNewActivite(activite)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ActiviteRecyclageViewModel::class.java)
        viewModel.getCreateActiviteObserver().observe(this, Observer<ActiviteRecyclage?> { activite ->
            if (activite == null) {
                Toast.makeText(this@AddActiviteRecyclage, "Failed to update Activity", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddActiviteRecyclage, "Successfully updated Activity", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        model.close() // Libérer les ressources du modèle
    }
}
