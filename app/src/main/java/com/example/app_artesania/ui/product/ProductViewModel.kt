package com.example.app_artesania.ui.product


import androidx.lifecycle.ViewModel
import com.example.app_artesania.R
import com.example.app_artesania.model.Product


class ProductViewModel : ViewModel() {
    val product = Product("Rana de cerámica", R.drawable.cosa, 34.00, "Esta encantadora rana de cerámica es una pieza artística única que aportará un toque de naturaleza y arte a cualquier espacio. Creada por artesanos locales, cada rana es moldeada, pintada y esmaltada a mano, lo que garantiza que no haya dos iguales.", "123ABC")
}

