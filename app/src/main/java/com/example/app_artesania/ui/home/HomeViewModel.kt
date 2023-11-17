package com.example.app_artesania.ui.home

import androidx.lifecycle.ViewModel
import com.example.app_artesania.R

class HomeViewModel : ViewModel() {

    data class Category(
        val name: String,
        val img: Int
    )

    data class Product(
        val name: String,
        val img: Int,
        val price: Int
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
            Product("Rana de cerámica", R.drawable.cosa, 34),
            Product("Lombrices caseras kelekffef", R.drawable.producto, 1),
            Product("Aguacate", R.drawable.producto3, 56),
            Product("P4", R.drawable.producto2, 100),
            Product("Productaso muy increible muy bueno increible 5", R.drawable.producto4, 7)
        )
    )

    val products2 = ArrayList(
        listOf(
            Product("Adasdjas", R.drawable.producto5, 34),
            Product("MAriposasas", R.drawable.producto4, 1),
            Product("Aguacate", R.drawable.producto, 56),
            Product("P4", R.drawable.producto3, 100),
            Product("Productaso muy increible muy bueno increible 5", R.drawable.producto4, 7)
        )
    )

    val craftsmans = ArrayList(
        listOf(
            Craftsman("Artesano1", R.drawable.artesano1),
            Craftsman("Pedro", R.drawable.artesano2),
            Craftsman("Artesano3", R.drawable.artesano3),
            Craftsman("Maria de los Angeles", R.drawable.artesano4),
            Craftsman("Paco5", R.drawable.artesano5)
        )
    )

}