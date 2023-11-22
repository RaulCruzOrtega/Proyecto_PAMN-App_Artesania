package com.example.app_artesania.model

import com.example.app_artesania.R

data class User(
    val name: String,
    val email: String,
    var isCraftsman: Boolean,
    val idCraftsman: String,
    val image: Any, //Cambiar cuando firebase esté listo
    val orders: ArrayList<String>,
    val products: ArrayList<String>
){
    constructor() : this("", "", false, "", "", ArrayList(), ArrayList())
    companion object {
        val a1: User = User(
            "Valentina",
            "val@gmail.com",
            true,
            "123ABC",
            R.drawable.artesano1,
            ArrayList<String>(),
            arrayListOf("1", "3")
        )
        val a2: User = User(
            "Juana",
            "juana@gmail.com",
            true,
            "111ABC",
            R.drawable.artesano2,
            ArrayList<String>(),
            arrayListOf("2")
        )
        val a3: User = User(
            "María",
            "mari@gmail.com",
            true,
            "222ABC",
            R.drawable.artesano3,
            ArrayList<String>(),
            arrayListOf("4")
        )
        val a4: User = User(
            "Paco",
            "paco@gmail.com",
            true,
            "333ABC",
            R.drawable.artesano4,
            ArrayList<String>(),
            arrayListOf("5")
        )
        val a5: User = User(
            "Carmen",
            "carmen@gmail.com",
            true,
            "444ABC",
            R.drawable.artesano5,
            ArrayList<String>(),
            arrayListOf("6")
        )
    }
}
