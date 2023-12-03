package com.example.app_artesania.model

data class Offer(
    var idCraftsman: String,
    val price: Double,
    val comment: String
){
    constructor() : this("", 0.0, "")
}