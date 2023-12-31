package com.example.app_artesania.model

data class User(
    var name: String,
    val email: String,
    var isCraftsman: Boolean,
    val idCraftsman: String,
    var image: String,
    val purchased: ArrayList<String>,
    var favoproducts: ArrayList<String>
){
    fun getisCraftsman(): Boolean{
        return isCraftsman
    }

    fun setisCraftsman(newisCraftsman: Boolean){
        isCraftsman = newisCraftsman
    }

    constructor() : this("", "", false, "", "", ArrayList(), ArrayList())
}
