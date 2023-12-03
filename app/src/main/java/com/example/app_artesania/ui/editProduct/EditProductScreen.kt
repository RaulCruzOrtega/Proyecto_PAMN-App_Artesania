package com.example.app_artesania.ui.editProduct

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.ui.createProduct.CreateProductBody
import com.example.app_artesania.ui.createProduct.CreateProductViewModel
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.CategoryDropdownMenu
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduct(viewModel: EditProductViewModel, navController: NavController){
    Scaffold (
        topBar = { SimpleTopNavBar(title = "Editar Producto", navController = navController)
        }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            EditProductBody(viewModel, navController)
        }
    }
}

@Composable
fun EditProductBody(viewModel: EditProductViewModel, navController: NavController){
    val loadState by viewModel.loadState.observeAsState()
    val name: String by viewModel.name.observeAsState(initial = "")
    val price: String by viewModel.price.observeAsState(initial = "")
    val category: Category by viewModel.category.observeAsState(initial = Category.Alfarería)
    val description: String by viewModel.description.observeAsState(initial = "")
    val image: String by viewModel.imageselect.observeAsState(initial = "")

    val nameError: Boolean by viewModel.nameError.observeAsState(initial = false)
    val priceError: Boolean by viewModel.priceError.observeAsState(initial = false)
    val descriptionError: Boolean by viewModel.descriptionError.observeAsState(initial = false)

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            LazyColumn (modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.Start)
            {
                item {
                    Text(text = "Detalles del producto", fontSize = 20.sp)
                    Spacer(modifier = Modifier.padding(16.dp))
                    NameField(name) { viewModel.onEditProductChanged(it, price, category, description) }
                    if(nameError){
                        Spacer(modifier = Modifier.padding(4.dp))
                        textError(texto = "Introduzca un Nombre para el producto")
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    PriceField(price) { viewModel.onEditProductChanged(name, it, category, description) }
                    if(priceError){
                        Spacer(modifier = Modifier.padding(4.dp))
                        textError(texto = "Introduzca un Precio para el producto")
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    CategoryDropdownMenu(category) { viewModel.onEditProductChanged(name, price, it, description) }
                    Spacer(modifier = Modifier.padding(16.dp))
                    DescriptionField(description) { viewModel.onEditProductChanged(name, price, category, it) }
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
                    ButtonEditProduct(viewModel, navController)
                }
            }
        }
        else -> {}
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
fun ButtonProductImage(viewModel: EditProductViewModel) {
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
fun ButtonEditProduct(viewModel: EditProductViewModel, navController: NavController) {
    Button(onClick = { viewModel.editarProducto(navController) }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Editar Producto")
    }
}