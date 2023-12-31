package com.example.app_artesania.ui.createOrder

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.Category
import com.example.app_artesania.ui.createProduct.CreateProductViewModel
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.CategoryDropdownMenu
import com.example.app_artesania.ui.templates.SimpleTopNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrderScreen(viewModel: CreateOrderViewModel, navController: NavController){
    Scaffold (
        topBar = { SimpleTopNavBar(title = "Nuevo Pedido", navController = navController)
        }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            CreateOrderBody(viewModel, navController)
        }
    }
}

@Composable
fun CreateOrderBody(viewModel: CreateOrderViewModel, navController: NavController){
    val title: String by viewModel.title.observeAsState(initial = "")
    val description: String by viewModel.description.observeAsState(initial = "")
    val category: Category by viewModel.category.observeAsState(initial = Category.Otro)
    val imageselect: String by viewModel.imageselect.observeAsState(initial = "")
    
    val titleError: Boolean by viewModel.titleError.observeAsState(initial = false)
    val descriptionError: Boolean by viewModel.descriptionError.observeAsState(initial = false)

    LazyColumn (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start)
    {
        item {
            Text(text = "Crea un nuevo Pedido", fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(16.dp))
            TitleField(title, titleError) { viewModel.onCreateOrderChanged(it, description, category) }
            Spacer(modifier = Modifier.padding(16.dp))
            DescriptionField(description, descriptionError) { viewModel.onCreateOrderChanged(title, it, category) }
            Spacer(modifier = Modifier.padding(16.dp))
            CategoryDropdownMenu(category) { viewModel.onCreateOrderChanged(title, description, it) }
            Spacer(modifier = Modifier.padding(16.dp))
            ButtonImage(viewModel)
            if(imageselect != ""){
                Text(text = imageselect)
            }
            Spacer(modifier = Modifier.padding(16.dp))
            ButtonCreateOrder(viewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleField(title: String, titleError: Boolean,onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = title, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Título del Pedido") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
    if(titleError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Introduzca un Título para el pedido")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionField(description: String, descriptionError: Boolean ,onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = description, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        label = { Text(text = "Descripción del Producto") },
        maxLines = 10
    )
    if(descriptionError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Introduzca una Descripción del producto")
    }
}

@Composable
fun ButtonImage(viewModel: CreateOrderViewModel) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){imageUri ->
        imageUri?.let {
            viewModel.imageselect(imageUri)
        }
    }

    OutlinedButton(onClick = { galleryLauncher.launch("image/*") }) {
        Text(text = "Seleccionar Imagen de la Galería")
    }
}

@Composable
fun ButtonCreateOrder(viewModel: CreateOrderViewModel, navController: NavController) {
    Button(
        onClick = { viewModel.createOrder(navController) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Crear Pedido")
    }
}