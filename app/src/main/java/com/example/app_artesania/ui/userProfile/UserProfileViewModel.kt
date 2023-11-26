package com.example.app_artesania.ui.userProfile

import androidx.lifecycle.ViewModel
import com.example.app_artesania.model.Product

class UserProfileViewModel : ViewModel()  {

    val products = ArrayList(
        listOf(
            Product.p1, Product.p2, Product.p3, Product.p4, Product.p5, Product.p6
        )
    )
}