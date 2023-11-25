package com.example.app_artesania.model

import com.example.app_artesania.R
import com.example.app_artesania.navigation.AppScreens

sealed class ItemsBottomNav(
    val icon: Int,
    val title: String,
    val route: String
){
    object homeIcon: ItemsBottomNav(
        R.drawable.homeicon,
        "HomeIcon",
        AppScreens.HomeScreen.route
    )
    object favsIcon: ItemsBottomNav(
        R.drawable.hearticon,
        "FavoIcon",
        AppScreens.ProductScreen.route + "/1"
    )
    object createProductIcon: ItemsBottomNav(
        R.drawable.crear_producto,
        "CreateProductIcon",
        AppScreens.CreateProductScreen.route
    )
    object ordersIcon: ItemsBottomNav(
        R.drawable.ordericon,
        "Orders",
        AppScreens.RegisterScreen.route
    )

    object userIcon: ItemsBottomNav(
        R.drawable.usericon,
        "Item4",
        AppScreens.CraftsmanProfileScreen.route
    )
}

