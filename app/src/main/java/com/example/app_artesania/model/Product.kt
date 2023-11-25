package com.example.app_artesania.model

import com.example.app_artesania.R

data class Product(
    val id: String,
    val name: String,
    val image: Any, //Cambiar cuando firebase esté listo
    val price: Double,
    val description: String,
    val idCraftsman: String
){
    constructor() : this("", "", "", 0.0, "", "")

    companion object {
        val p1: Product = Product("1","Rana de cerámica", R.drawable.cosa, 34.00, "Esta encantadora rana de cerámica es una pieza artística única que aportará un toque de naturaleza y arte a cualquier espacio. Creada por artesanos locales, cada rana es moldeada, pintada y esmaltada a mano, lo que garantiza que no haya dos iguales.", "123ABC")
        val p2: Product = Product("2", "Lombrices caseras kelekffef", R.drawable.producto, 1.00, "Descripción", "123ABC")
        val p3: Product = Product("3", "Aguacate", R.drawable.producto3, 56.00, "Descripción", "123ABC")
        val p4: Product = Product("4","P4", R.drawable.producto2, 100.00, "Descripción", "123ABC")
        val p5: Product = Product("5","Productaso muy increíble muy bueno increíble 5", R.drawable.producto4, 7.00, "Descripción", "123ABC")
        val p6: Product = Product("6","Adasdjas", R.drawable.producto5, 34.00, "Descripción", "123ABC")
    }
}