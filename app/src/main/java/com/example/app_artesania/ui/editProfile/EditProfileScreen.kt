package com.example.app_artesania.ui.editProfile

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel, navController: NavController){
    Scaffold (
        topBar = { SimpleTopNavBar(title = "Editar Perfil", navController = navController)
        }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            EditProfileBody(viewModel, navController)
        }
    }
}

@Composable
fun EditProfileBody(viewModel: EditProfileViewModel, navController: NavController) {
    val userName: String by viewModel.userName.observeAsState(initial = "")

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.Start)
    {
        Text(text = "Editar Perfil de Usuario", fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Modificar Nombre de usuario:", fontSize = 15.sp)
        UserNameField(userName) { viewModel.onCreateProductChanged(it) }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Cambiar Foto de Perfil:", fontSize = 15.sp)
        ButtonImage()
        Spacer(modifier = Modifier.padding(16.dp))
        ButtonModifyUser()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameField(userName: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = userName, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nuevo Nombre de Usuario") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun ButtonImage() {
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
fun ButtonModifyUser() {
    Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Modificar  Perfil")
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        EditProfileScreen(EditProfileViewModel(), navController)
    }
}