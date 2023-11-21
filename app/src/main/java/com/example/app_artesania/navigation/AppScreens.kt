package com.example.app_artesania.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object HomeScreen: AppScreens("home_screen")
    object CraftsmanProfileScreen: AppScreens("craftsmanProfile_screen")
    object ProductScreen: AppScreens("product_screen")
}
