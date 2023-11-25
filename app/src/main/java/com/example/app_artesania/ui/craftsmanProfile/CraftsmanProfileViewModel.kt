package com.example.app_artesania.ui.craftsmanProfile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_artesania.data.SingOut
import com.example.app_artesania.model.Product
import com.example.app_artesania.navigation.AppScreens

class CraftsmanProfileViewModel: ViewModel()  {

    val products = ArrayList(
        listOf(
            Product.p1, Product.p2, Product.p3, Product.p4, Product.p5, Product.p6
        )
    )

    fun cerrarSesión(navController: NavController){
        SingOut()
        navController.navigate(route = AppScreens.LoginScreen.route)
    }

}