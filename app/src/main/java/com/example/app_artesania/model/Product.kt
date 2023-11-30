package com.example.app_artesania.model

data class Product(
    var id: String,
    val name: String,
    val image: String,
    val price: Double,
    val description: String,
    val idCraftsman: String
){
    constructor() : this("", "", "", 0.0, "", "")
}

data class newProducto(
    val name: String,
    val image: String,
    val price: Double,
    val category: String,
    val description: String,
    val idCraftsman: String
)