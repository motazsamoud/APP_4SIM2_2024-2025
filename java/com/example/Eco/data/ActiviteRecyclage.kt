package com.example.Eco.data

data class ActiviteRecyclage(
    val _id: String? = null,
    val dateAndTime: String,
    val quantity: Int,
    val recyclableMaterial: String,
    val user: String,
    val image: String // Ajout d'un champ pour l'image (URL de l'image)
)
