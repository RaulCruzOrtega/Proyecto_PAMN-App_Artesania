package com.example.app_artesania.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.ui.home.HomeScreen
import com.example.app_artesania.ui.home.HomeViewModel
import com.example.app_artesania.ui.login.LoginScreen
import com.example.app_artesania.ui.login.LoginViewModel
import com.example.app_artesania.ui.register.RegisterScreen
import com.example.app_artesania.ui.register.RegisterViewModel


@Composable
fun AppNavigation() {
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = AppScreens.HomeScreen.route){
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(LoginViewModel(), navControler)
        }
        composable(route = AppScreens.RegisterScreen.route){
            RegisterScreen(RegisterViewModel(), navControler)
        }
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(HomeViewModel(), navControler)
        }
    }
}