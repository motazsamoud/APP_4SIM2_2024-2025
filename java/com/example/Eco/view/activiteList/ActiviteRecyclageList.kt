package com.example.Eco.view.activiteList

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument

import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.Eco.view.addActivite.AddActiviteRecyclage
import com.example.Eco.view.ratingActivite.AddRatingActivite
import com.example.Eco.view.stat.Statistique
import com.example.Eco.viewmodel.ActiviteRecyclageViewModel
import com.example.Eco.databinding.ListActiviterecyclageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.Eco.view.LoginActivite.ChangePasswordActivity
import com.example.Eco.view.chatbot.ChatbotActivity
import java.io.InputStream

import java.util.Locale

class ActiviteRecyclageList : AppCompatActivity() {
    lateinit var recyclerAdapter: ActiviteRecyclageAdapter
    private lateinit var binding: ListActiviterecyclageBinding
    private lateinit var speechRecognizer: SpeechRecognizer

    companion object {
        const val REQUEST_CODE_SPEECH_INPUT = 100
        const val PERMISSION_REQUEST_CODE = 200
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation du View Binding
        binding = ListActiviterecyclageBinding.inflate(layoutInflater)
        setContentView(binding.root)
// Initialiser la reconnaissance vocale
        checkAudioPermission()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        binding.voiceCommand.setOnClickListener {
            startVoiceRecognition()
        }
        initRecyclerView()
        initViewModel()
        binding.textView5.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        // Bouton pour voir les activités de l'utilisateur
        binding.Myactivities.setOnClickListener {
            val intent = Intent(this@ActiviteRecyclageList, ActiviteRecyclageListUser::class.java)
            startActivity(intent)
        }

        // Bouton pour ajouter une nouvelle activité
        binding.addActivite.setOnClickListener {
            val intent = Intent(this@ActiviteRecyclageList, AddActiviteRecyclage::class.java)
            startActivity(intent)
        }

        // Bouton pour voir les statistiques
        binding.stats.setOnClickListener {
            val intent = Intent(this@ActiviteRecyclageList, Statistique::class.java)
            startActivity(intent)
        }

        // Bouton pour exporter la liste des activités en PDF
        binding.exportPDF.setOnClickListener {
            exportPDF()
        }
        // Dans la méthode onCreate
        binding.chatbotButton.setOnClickListener {
            val intent = Intent(this@ActiviteRecyclageList, ChatbotActivity::class.java)
            startActivity(intent)
        }

    }



    // Méthode pour démarrer la reconnaissance vocale
    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast.makeText(this, "Erreur lors du démarrage de la reconnaissance vocale", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val command = result?.get(0)?.toLowerCase(Locale.getDefault())

            command?.let {
                handleVoiceCommand(it)
            }
        }
    }
    // Méthode pour gérer les commandes vocales
    private fun handleVoiceCommand(command: String) {
        when {
            command.contains("B") -> {
                val intent = Intent(this, ActiviteRecyclageListUser::class.java)
                startActivity(intent)
            }
            command.contains("C") -> {
                val intent = Intent(this, AddActiviteRecyclage::class.java)
                startActivity(intent)
            }
            command.contains("D") || command.contains("stats") -> {
                val intent = Intent(this, Statistique::class.java)
                startActivity(intent)
            }

        }
    }
    // Vérification de la permission d'enregistrement audio
    private fun checkAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSION_REQUEST_CODE
                )
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show()
        }
    }
    // Méthode pour exporter la liste des activités en PDF
    // Méthode pour exporter la liste des activités en PDF
    // Méthode pour exporter la liste des activités en PDF
    private fun exportPDF() {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Taille A4 en points
        val page = document.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        paint.textSize = 16f
        canvas.drawText("Liste des activités de recyclage", 80f, 50f, paint)

        val activites = recyclerAdapter.getActiviteList()

        var yPosition = 100f
        paint.textSize = 12f
        for (activite in activites) {
            // Détails de l'activité
            val activiteDetails = "Date: ${activite.dateAndTime}, Matériel: ${activite.recyclableMaterial}, Quantité: ${activite.quantity}"
            canvas.drawText(activiteDetails, 80f, yPosition, paint)
            yPosition += 20f

            // Journal de l'URI de l'image pour déboguer
            Log.d("PDFExport", "Image URI: ${activite.image}")

            // Charger et dessiner l'image associée à l'activité
            val imageUri = Uri.parse(activite.image)
            try {
                val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
                if (inputStream != null) {
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    if (bitmap != null) {
                        // Redimensionner l'image
                        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                        canvas.drawBitmap(scaledBitmap, 80f, yPosition, paint)
                        yPosition += 120f
                    } else {
                        Log.e("PDFExport", "Impossible de décoder le bitmap à partir de l'URI")
                        Toast.makeText(this, "Erreur lors du décodage de l'image", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("PDFExport", "InputStream est null pour l'URI : $imageUri")
                    Toast.makeText(this, "Erreur lors de la lecture de l'image", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("PDFExport", "Erreur lors du chargement de l'image : ${e.message}")
                e.printStackTrace()
                Toast.makeText(this, "Erreur lors du chargement de l'image : ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        document.finishPage(page)

        val filePath = File(getExternalFilesDir(null), "ActivitesRecyclage.pdf")
        try {
            document.writeTo(FileOutputStream(filePath))
            Toast.makeText(this, "PDF exporté vers : ${filePath.absolutePath}", Toast.LENGTH_LONG).show()

            // Ouvrir le PDF
            viewPDF(filePath)

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Erreur lors de l'exportation du PDF", Toast.LENGTH_LONG).show()
        }

        document.close()
    }




    // Méthode pour afficher le PDF exporté
    private fun viewPDF(file: File) {
        val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Aucune application trouvée pour ouvrir les fichiers PDF", Toast.LENGTH_LONG).show()
        }
    }

    private fun initRecyclerView() {
        binding.activiteRecyclageRecycleview.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = ActiviteRecyclageAdapter(this)
        binding.activiteRecyclageRecycleview.adapter = recyclerAdapter
        recyclerAdapter.setOnItemClickListener(object : ActiviteRecyclageAdapter.OnItemClickListener {
            override fun onItemClick(
                activiteId: String,
                activiteDate: String,
                activiteMaterial: String,
                quantity: Int,
                image: String

            ) {
                val intent = Intent(this@ActiviteRecyclageList, AddRatingActivite::class.java)
                intent.putExtra("activiteId", activiteId)
                startActivity(intent)
            }



            override fun onButtonClick(position: Int) {
                // Logique supplémentaire pour les boutons
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: ActiviteRecyclageViewModel = ViewModelProvider(this).get(ActiviteRecyclageViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setActiviteList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Erreur lors de la récupération de la liste", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getActivites()
    }
}
