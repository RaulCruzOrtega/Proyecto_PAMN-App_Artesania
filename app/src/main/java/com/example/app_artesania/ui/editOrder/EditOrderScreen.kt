package com.example.app_artesania.ui.editOrder

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
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.ui.editProduct.EditProductViewModel
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.CategoryDropdownMenu
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOrderScreen(viewModel: EditOrderViewModel, navController: NavController){
    Scaffold (
        topBar = { SimpleTopNavBar(title = "Editar Pedido", navController = navController)
        }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            EditOrderBody(viewModel, navController)
        }
    }
}

@Composable
fun EditOrderBody(viewModel: EditOrderViewModel, navController: NavController){
    val loadState by viewModel.loadState.observeAsState()
    val title: String by viewModel.title.observeAsState(initial = "")
    val description: String by viewModel.description.observeAsState(initial = "")
    val category: Category by viewModel.category.observeAsState(initial = Category.Otro)

    val titleError: Boolean by viewModel.titleError.observeAsState(initial = false)
    val descriptionError: Boolean by viewModel.descriptionError.observeAsState(initial = false)

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            LazyColumn (modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start)
            {
                item {
                    Text(text = "Detalles del producto", fontSize = 20.sp)
                    Spacer(modifier = Modifier.padding(16.dp))
                    TitleField(title, titleError) { viewModel.onEditOrderChanged(it, description, category) }
                    Spacer(modifier = Modifier.padding(16.dp))
                    DescriptionField(description, descriptionError) { viewModel.onEditOrderChanged(title, it, category) }
                    Spacer(modifier = Modifier.padding(16.dp))
                    CategoryDropdownMenu(category) { viewModel.onEditOrderChanged(title, description, it) }
                    Spacer(modifier = Modifier.padding(16.dp))
                    ButtonEditOrder(viewModel, navController)
                }
            }
        }
        else -> {}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleField(title: String, titleError: Boolean, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = title, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nombre del Pedido") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
    if(titleError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Introduzca un título para el pedido")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionField(description: String, descriptionError: Boolean, onTextFieldChanged: (String) -> Unit) {
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
        textError(texto = "Introduzca una descripción del pedido")
    }
}

@Composable
fun ButtonEditOrder(viewModel: EditOrderViewModel, navController: NavController) {
    Button(onClick = { viewModel.editOrder(navController) }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Editar Pedido")
    }
}