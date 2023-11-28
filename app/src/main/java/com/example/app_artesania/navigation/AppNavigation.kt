package com.example.app_artesania.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_artesania.ui.EditProfile.EditProfileScreen
import com.example.app_artesania.ui.EditProfile.EditProfileViewModel
import com.example.app_artesania.ui.createProduct.CreateProduct
import com.example.app_artesania.ui.createProduct.CreateProductViewModel
import com.example.app_artesania.ui.home.HomeScreen
import com.example.app_artesania.ui.home.HomeViewModel
import com.example.app_artesania.ui.login.LoginScreen
import com.example.app_artesania.ui.login.LoginViewModel
import com.example.app_artesania.ui.product.ProductScreen
import com.example.app_artesania.ui.product.ProductViewModel
import com.example.app_artesania.ui.register.RegisterScreen
import com.example.app_artesania.ui.register.RegisterViewModel
import com.example.app_artesania.ui.userProfile.UserProfileScreen
import com.example.app_artesania.ui.userProfile.UserProfileViewModel


@Composable
fun AppNavigation() {
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = AppScreens.LoginScreen.route){
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(LoginViewModel(navControler), navControler)
        }
        composable(route = AppScreens.RegisterScreen.route){
            RegisterScreen(RegisterViewModel(), navControler)
        }
        composable(route = AppScreens.HomeScreen.route){
            println("Redirigiendo a HOME")
            HomeScreen(HomeViewModel(), navControler)
        }
        composable(route = AppScreens.CreateProductScreen.route){
            CreateProduct(CreateProductViewModel(), navControler)
        }
        composable(route = AppScreens.UserProfileScreen.route){
            UserProfileScreen(UserProfileViewModel(), navControler)
        }
        composable(route = AppScreens.EditProfileScreen.route){
            EditProfileScreen(EditProfileViewModel(), navControler)
        }
        composable(route = AppScreens.ProductScreen.route + "/{productId}",
            arguments = listOf(navArgument(name = "productId"){
                type = NavType.StringType
            })){
            val ProductViewModel: ProductViewModel = ProductViewModel(
                    it.arguments?.getString("productId")
                )
            ProductScreen(ProductViewModel, navControler)
        }
    }
}