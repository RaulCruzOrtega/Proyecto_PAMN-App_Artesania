package com.example.app_artesania.model

data class Product(
    val name: String,
    val image: Any, //Cambiar cuando firebase esté listo
    val price: Double,
    val description: String,
    val idCraftsman: String
)
