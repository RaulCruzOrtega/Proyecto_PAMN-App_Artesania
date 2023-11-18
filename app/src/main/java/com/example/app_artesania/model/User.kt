package com.example.app_artesania.model

data class User(
    val name: String,
    val email: String,
    val image: String = "",
    val orders: ArrayList<String> = ArrayList<String>()
)
