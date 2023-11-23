package com.example.app_artesania.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_artesania.ui.craftsmanProfile.CraftsmanProfileScreen
import com.example.app_artesania.ui.craftsmanProfile.CraftsmanProfileViewModel
import com.example.app_artesania.ui.home.HomeScreen
import com.example.app_artesania.ui.home.HomeViewModel
import com.example.app_artesania.ui.login.LoginScreen
import com.example.app_artesania.ui.login.LoginViewModel
import com.example.app_artesania.ui.product.ProductScreen
import com.example.app_artesania.ui.product.ProductViewModel
import com.example.app_artesania.ui.register.RegisterScreen
import com.example.app_artesania.ui.register.RegisterViewModel


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
            HomeScreen(HomeViewModel(), navControler)
        }
        composable(route = AppScreens.CraftsmanProfileScreen.route){
            CraftsmanProfileScreen(CraftsmanProfileViewModel(), navControler)
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