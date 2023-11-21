package com.example.app_artesania.ui.home

import androidx.lifecycle.ViewModel
import com.example.app_artesania.R
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User

class HomeViewModel : ViewModel() {

    data class Category(
        val name: String,
        val img: Int
    )

    data class Craftsman(
        val name: String,
        val img: Int
    )

    val categories = ArrayList(
        listOf(
            Category("Textiles", R.drawable.ropa),
            Category("Cerámica", R.drawable.ceramica),
            Category("Esculturas", R.drawable.escultura),
            Category("Joyas", R.drawable.joyas),
            Category("Juguetes", R.drawable.juguete),
            Category("Cestería", R.drawable.cesta)
        )
    )

    val products = ArrayList(
        listOf(
            Product.p1, Product.p2, Product.p3, Product.p4, Product.p5, Product.p6
        )
    )

    val products2 = ArrayList(
        listOf(
            Product.p6, Product.p5, Product.p2, Product.p4
        )
    )

    val craftsmans = ArrayList(
        listOf(
            User.a1, User.a2, User.a3, User.a4, User.a5
        )
    )

}