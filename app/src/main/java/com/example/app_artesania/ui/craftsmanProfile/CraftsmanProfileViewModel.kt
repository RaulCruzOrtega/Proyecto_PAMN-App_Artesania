package com.example.app_artesania.ui.craftsmanProfile

import androidx.lifecycle.ViewModel
import com.example.app_artesania.R
import com.example.app_artesania.model.Product

class CraftsmanProfileViewModel: ViewModel()  {

    val products = ArrayList(
        listOf(
            Product("Rana de cerámica", R.drawable.cosa, 34.00, "Descripción", "123ABC"),
            Product("Lombrices caseras kelekffef", R.drawable.producto, 1.00, "Descripción", "123ABC"),
            Product("Aguacate", R.drawable.producto3, 56.00, "Descripción", "123ABC"),
            Product("P4", R.drawable.producto2, 100.00, "Descripción", "123ABC"),
            Product("Productaso muy increible muy bueno increible 5", R.drawable.producto4, 7.00, "Descripción", "123ABC")
        )
    )
}