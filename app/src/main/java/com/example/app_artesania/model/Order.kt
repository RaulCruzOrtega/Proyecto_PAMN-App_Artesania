package com.example.app_artesania.model

data class Order(
    var id: String,
    val title: String,
    val description: String,
    val category: String,
    val userId: String
){
    constructor() : this("", "", "",  "", "")
}