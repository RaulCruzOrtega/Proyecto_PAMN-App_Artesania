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
        "Item1",
        AppScreens.HomeScreen.route
    )
    object favsIcon: ItemsBottomNav(
        R.drawable.hearticon,
        "Item2",
        AppScreens.LoginScreen.route
    )
    object ordersIcon: ItemsBottomNav(
        R.drawable.ordericon,
        "Item3",
        AppScreens.RegisterScreen.route
    )

    object userIcon: ItemsBottomNav(
        R.drawable.usericon,
        "Item4",
        AppScreens.CraftsmanProfileScreen.route
    )
}

