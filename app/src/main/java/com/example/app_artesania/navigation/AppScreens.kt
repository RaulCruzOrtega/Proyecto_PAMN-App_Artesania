package com.example.app_artesania.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object HomeScreen: AppScreens("home_screen")
    object ProductScreen: AppScreens("product_screen")
    object CreateProductScreen: AppScreens("createProduct_screen")
    object UserProfileScreen: AppScreens("userProfile_screen")
    object EditProfileScreen: AppScreens("editProfile_screen")
    object EditProductScreen: AppScreens("editProduct_screen")

    object CategoryScreen: AppScreens("category_screen")
}
