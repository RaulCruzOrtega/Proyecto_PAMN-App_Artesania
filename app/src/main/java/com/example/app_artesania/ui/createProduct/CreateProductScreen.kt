package com.example.app_artesania.ui.createProduct

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.Category.Alfarería.getCategories
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

    LazyColumn (modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.Start)
    {
        item {
            Text(text = "Detalles del producto", fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(16.dp))
            NameField(name) { viewModel.onCreateProductChanged(it, price, category, description) }
            Spacer(modifier = Modifier.padding(16.dp))
            PriceField(price) { viewModel.onCreateProductChanged(name, it, category, description) }
            Spacer(modifier = Modifier.padding(16.dp))
            DropdownCategoryMenu(category) { viewModel.onCreateProductChanged(name, price, it, description) }
            Spacer(modifier = Modifier.padding(16.dp))
            DescriptionField(description) { viewModel.onCreateProductChanged(name, price, category, it) }
            Spacer(modifier = Modifier.padding(16.dp))
            ButtonProductImage()
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
fun DropdownCategoryMenu(selectedCategory: Category, onCategorySelected: (Category) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val categories: ArrayList<Category> = getCategories()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text("Selecciona una categoría")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .clickable { expanded = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = selectedCategory.categoryType.name,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category.categoryType.name) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ButtonProductImage() {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){imageUri ->
        imageUri?.let {
            println(imageUri)
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