package com.example.app_artesania.model

data class Order(
    var id: String,
    val title: String,
    val description: String,
    val category: String,
    val userEmail: String,
    val offers: ArrayList<Offer>
){
    constructor() : this("", "", "",  "", "", ArrayList<Offer>())
}

data class newOrder(
    val title: String,
    val description: String,
    val category: String,
    val userEmail: String,
    val offers: ArrayList<Offer>
)