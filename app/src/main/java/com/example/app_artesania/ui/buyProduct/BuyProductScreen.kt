package com.example.app_artesania.ui.buyProduct

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyProductScreen(viewModel: BuyProductViewModel = viewModel(), navController: NavController) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pagar con Tarjeta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text("Número de Tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Fecha de Vencimiento") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (viewModel.isCardNumberValid(cardNumber) &&
                        viewModel.isExpiryDateValid(expiryDate) &&
                        viewModel.isCvvValid(cvv)) {
                        if (viewModel.buyProduct(cardNumber, expiryDate, cvv)) {
                            showConfirmationDialog = true
                            errorMessage = null
                        } else {
                            errorMessage = "Error al procesar la compra"
                        }
                    } else {
                        errorMessage = "Datos de tarjeta inválidos"
                    }
                }
            ) {
                Text("Comprar Producto")
            }

            if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red)
            }
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                showConfirmationDialog = false
                navController.navigate(AppScreens.HomeScreen.route) {
                    popUpTo(AppScreens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            },
            title = { Text("Compra Realizada") },
            text = { Text("Tu pedido ha sido realizado con éxito.") },
            confirmButton = {
                Button(onClick = {
                    showConfirmationDialog = false
                    navController.navigate(AppScreens.HomeScreen.route) {
                        popUpTo(AppScreens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BuyProductScreenPreview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        BuyProductScreen(BuyProductViewModel("1slFS2X3THA2jvLPSmcI"), navController)
    }
}


