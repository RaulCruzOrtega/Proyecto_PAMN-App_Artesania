package com.example.app_artesania.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.ui.login.LoginScreen
import com.example.app_artesania.ui.login.LoginViewModel
import com.example.app_artesania.ui.register.RegisterScreen


@Composable
fun AppNavigation() {
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = AppScreens.LoginScreen.route){
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(LoginViewModel(), navControler)
        }
        composable(route = AppScreens.RegisterScreen.route){
            RegisterScreen(navControler)
        }
    }
}