package com.example.app_artesania.ui.product


import androidx.lifecycle.ViewModel
import com.example.app_artesania.data.TemporalDatabase
import com.example.app_artesania.model.Product


class ProductViewModel(productId: String?) : ViewModel() {
    val product: Product = TemporalDatabase.getProduct("1")!!
}

