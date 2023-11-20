package com.example.app_artesania.model

data class User(
    val name: String,
    val email: String,
    val isCraftsman: Boolean,
    val idCraftsman: String,
    val image: String = "",
    val orders: ArrayList<String> = ArrayList<String>(),
    val products: ArrayList<String> = ArrayList<String>()
)
