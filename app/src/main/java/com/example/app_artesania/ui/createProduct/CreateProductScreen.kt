package com.example.app_artesania.ui.createProduct

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProductBody(viewModel: CreateProductViewModel, navController: NavController){
    val name: String by viewModel.name.observeAsState(initial = "")
    val price: String by viewModel.price.observeAsState(initial = "")
    val description: String by viewModel.description.observeAsState(initial = "")

    LazyColumn (modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        item {
            Text(text = "Agregar un nuevo producto")
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Nombre del producto")
            NameField(name) { viewModel.onCreateProductChanged(it, price, description) }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Precio")
            PriceField(price) { viewModel.onCreateProductChanged(name, it, description) }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Descripción del producto")
            DescriptionField(description) { viewModel.onCreateProductChanged(name, price, it) }
            DropdownCategoryMenu()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(name: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = name, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Nombre del Producto") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceField(price: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = price, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Precio del Producto (€)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionField(description: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = description, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Descripción del Producto") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        maxLines = 10
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCategoryMenu() {
    var expanded by remember { mutableStateOf(false) }
    val categories: ArrayList<Category> = getCategories()
    var selectedCategory by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = { selectedCategory = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()
                },
            label = { Text(text = "Selecciona una Categoría") },
            trailingIcon = {
                Icon(icon, "", Modifier.clickable { expanded = !expanded } )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(textFiledSize.width.dp)
        ) {
            categories.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label.categoryType.name) },
                    onClick = {
                        selectedCategory = label.categoryType.name
                        expanded = false
                    }
                )
            }
        }
    }
}