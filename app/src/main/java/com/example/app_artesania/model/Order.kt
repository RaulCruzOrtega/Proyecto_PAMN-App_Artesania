package com.example.app_artesania.model

data class Order(
    var id: String,
    val title: String,
    val description: String,
    val category: String,
    val userEmail: String,
    val offers: ArrayList<Offer>,
    var isAssigned: Boolean,
    var image: String
){
    constructor() : this("", "", "",  "", "", ArrayList<Offer>(), false, "")
}

data class newOrder(
    val title: String,
    val description: String,
    val category: String,
    val userEmail: String,
    val offers: ArrayList<Offer>,
    val isAssigned: Boolean,
    var image: String
)