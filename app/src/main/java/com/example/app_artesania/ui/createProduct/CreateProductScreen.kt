package com.example.app_artesania.ui.createProduct

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_artesania.ui.register.Register
import com.example.app_artesania.ui.register.RegisterViewModel
import com.example.app_artesania.ui.templates.SimpleTopNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(viewModel: CreateProductViewModel, navController: NavController){
    Scaffold (
        topBar = { SimpleTopNavBar(title = "Crear Producto", navController = navController)
        }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            CreateProductBody(viewModel, navController)
        }
    }
}

@Composable
fun CreateProductBody(viewModel: CreateProductViewModel, navController: NavController){
    Text(text = "PRODUCTO HEAVY")
}