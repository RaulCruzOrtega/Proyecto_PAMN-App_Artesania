package com.example.app_artesania.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_artesania.ui.buyProduct.BuyProductScreen
import com.example.app_artesania.ui.buyProduct.BuyProductViewModel
import com.example.app_artesania.ui.category.CategoryScreen
import com.example.app_artesania.ui.category.CategoryViewModel
import com.example.app_artesania.ui.createOrder.CreateOrderScreen
import com.example.app_artesania.ui.createOrder.CreateOrderViewModel
import com.example.app_artesania.ui.editProfile.EditProfileScreen
import com.example.app_artesania.ui.editProfile.EditProfileViewModel
import com.example.app_artesania.ui.createProduct.CreateProduct
import com.example.app_artesania.ui.createProduct.CreateProductViewModel
import com.example.app_artesania.ui.editOrder.EditOrderScreen
import com.example.app_artesania.ui.editOrder.EditOrderViewModel
import com.example.app_artesania.ui.editProduct.EditProduct
import com.example.app_artesania.ui.editProduct.EditProductViewModel
import com.example.app_artesania.ui.home.HomeScreen
import com.example.app_artesania.ui.home.HomeViewModel
import com.example.app_artesania.ui.infoCrafsman.InfoCrafsmanScreen
import com.example.app_artesania.ui.infoCrafsman.InfoCrafsmanViewModel
import com.example.app_artesania.ui.login.LoginScreen
import com.example.app_artesania.ui.login.LoginViewModel
import com.example.app_artesania.ui.order.OrderScreen
import com.example.app_artesania.ui.order.OrderViewModel
import com.example.app_artesania.ui.orders.OrdersScreen
import com.example.app_artesania.ui.orders.OrdersViewModel
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
        composable(route = AppScreens.OrdersScreen.route){
            OrdersScreen(OrdersViewModel(), navControler)
        }
        composable(route = AppScreens.CreateOrderScreen.route){
            CreateOrderScreen(CreateOrderViewModel(), navControler)
        }
        composable(route = AppScreens.BuyProductScreen.route){
            BuyProductScreen(BuyProductViewModel(), navControler)
        }

        composable(route = AppScreens.ProductScreen.route + "/{productId}",
            arguments = listOf(navArgument(name = "productId"){
                type = NavType.StringType
            })){
            val ProductViewModel: ProductViewModel = ProductViewModel(
                it.arguments?.getString("productId"), navControler)
            ProductScreen(ProductViewModel, navControler)
        }
        composable(route = AppScreens.EditProductScreen.route + "/{productId}",
            arguments = listOf(navArgument(name = "productId"){
                type = NavType.StringType
            })){
            val EditProductViewModel: EditProductViewModel = EditProductViewModel(
                it.arguments?.getString("productId")!!)
            EditProduct(EditProductViewModel, navControler)
        }
        composable(route = AppScreens.CategoryScreen.route + "/{category}",
            arguments = listOf(navArgument(name = "category"){
                type = NavType.StringType
            })){
            val CategoryViewModel: CategoryViewModel = CategoryViewModel(
                it.arguments?.getString("category"), navControler)
            CategoryScreen(CategoryViewModel, navControler)
        }
        composable(route = AppScreens.OrderScreen.route + "/{orderId}",
            arguments = listOf(navArgument(name = "orderId"){
                type = NavType.StringType
            })){
            val OrderViewModel: OrderViewModel = OrderViewModel(
                it.arguments?.getString("orderId"), navControler)
            OrderScreen(OrderViewModel, navControler)
        }

        composable(route = AppScreens.CraftsmanProfileScreen.route + "/{craftsmanId}",
            arguments = listOf(navArgument(name = "craftsmanId") { type = NavType.StringType })
        ) {
            val craftsmanId = it.arguments?.getString("craftsmanId")
            val viewModel = InfoCrafsmanViewModel(craftsmanId, navControler)
            InfoCrafsmanScreen(viewModel, navControler)
        }
        composable(route = AppScreens.EditOrderScreen.route + "/{orderId}",
            arguments = listOf(navArgument(name = "orderId"){
                type = NavType.StringType
            })){
            val EditOrderViewModel: EditOrderViewModel = EditOrderViewModel(
                it.arguments?.getString("orderId"))
            EditOrderScreen(EditOrderViewModel, navControler)
        }
    }
}
