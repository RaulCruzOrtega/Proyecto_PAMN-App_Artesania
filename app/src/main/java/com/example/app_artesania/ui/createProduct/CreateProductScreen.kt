package com.example.app_artesania.ui.createProduct

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.Category
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.CategoryDropdownMenu
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
    val name: String by viewModel.name.observeAsState(initial = "")
    val price: String by viewModel.price.observeAsState(initial = "")
    val category: Category by viewModel.category.observeAsState(initial = Category.Alfarería)
    val description: String by viewModel.description.observeAsState(initial = "")
    val image: String by viewModel.imageselect.observeAsState(initial = "")

    val nameError: Boolean by viewModel.nameError.observeAsState(initial = false)
    val priceError: Boolean by viewModel.priceError.observeAsState(initial = false)
    val descriptionError: Boolean by viewModel.descriptionError.observeAsState(initial = false)

    LazyColumn (modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.Start)
    {
        item {
            Text(text = "Detalles del producto", fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(16.dp))
            NameField(name) { viewModel.onCreateProductChanged(it, price, category, description) }
            if(nameError){
                Spacer(modifier = Modifier.padding(4.dp))
                textError(texto = "Introduzca un Nombre para el producto")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            PriceField(price) { viewModel.onCreateProductChanged(name, it, category, description) }
            if(priceError){
                Spacer(modifier = Modifier.padding(4.dp))
                textError(texto = "Introduzca un Precio para el producto")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            CategoryDropdownMenu(category) { viewModel.onCreateProductChanged(name, price, it, description) }
            Spacer(modifier = Modifier.padding(16.dp))
            DescriptionField(description) { viewModel.onCreateProductChanged(name, price, category, it) }
            if(descriptionError){
                Spacer(modifier = Modifier.padding(4.dp))
                textError(texto = "Introduzca una Descripción del producto")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            ButtonProductImage(viewModel)
            if (image != "") {
                Text(text = image)
            }
            Spacer(modifier = Modifier.padding(16.dp))
            ButtonCreateProduct(viewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(name: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = name, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nombre del Producto") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceField(price: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = price, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Precio del Producto (€)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionField(description: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = description, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        label = { Text(text = "Descripción del Producto") },
        maxLines = 10
    )
}

@Composable
fun ButtonProductImage(viewModel: CreateProductViewModel) {
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
fun ButtonCreateProduct(viewModel: CreateProductViewModel, navController: NavController) {
    Button(onClick = { viewModel.crearProducto(navController) }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Añadir Producto")
    }
}