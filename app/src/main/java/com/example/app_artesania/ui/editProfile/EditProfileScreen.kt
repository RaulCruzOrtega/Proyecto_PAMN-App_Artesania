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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel, navController: NavController){
    Scaffold (
        topBar = { SimpleTopNavBar(title = "Editar Perfil de Usuario", navController = navController)
        }) {
        Box( Modifier.fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            EditProfileBody(viewModel, navController)
        }
    }
}

@Composable
fun EditProfileBody(viewModel: EditProfileViewModel, navController: NavController) {
    val userName: String by viewModel.userName.observeAsState(initial = "")
    val coroutineScope = rememberCoroutineScope()
    val imageSelect: String by viewModel.imageselect.observeAsState(initial = "")
    val oldPassword: String by viewModel.oldPassword.observeAsState(initial = "")
    val password1: String by viewModel.password1.observeAsState(initial = "")
    val password2: String by viewModel.password2.observeAsState(initial = "")
    val passwordError: Boolean by viewModel.passwordError.observeAsState(initial = false)
    val passwordRepError: Boolean by viewModel.passwordRepError.observeAsState(initial = false)
    val oldPasswordError: Boolean by viewModel.oldPasswordError.observeAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Modificar Nombre de usuario:", fontSize = 15.sp)
        UserNameField(userName) { viewModel.onNameChanged(it) }
        ButtonModifyUser{
            coroutineScope.launch {
                viewModel.editUserName(navController)
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Cambiar Foto de Perfil:", fontSize = 15.sp)
        ButtonImage(viewModel)
        if (imageSelect.isNotEmpty()){
            Text(text = imageSelect, color = Color.Gray, fontSize = 8.sp)
        }
        ButtonModifyImage{
            coroutineScope.launch {
                viewModel.editUserImage(navController)
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Modificar Contraseña:", fontSize = 15.sp)
        OldPasswordField(oldPassword, oldPasswordError) { viewModel.onPasswordChanged(it, password1, password2) }
        PasswordField1(password1, passwordError) { viewModel.onPasswordChanged(oldPassword, it, password2) }
        PasswordField2(password2, passwordRepError) { viewModel.onPasswordChanged(oldPassword, password1, it) }

        ButtonModifyPassword{
            coroutineScope.launch {
                viewModel.editPassword(navController)
            }
        }
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
fun ButtonModifyUser(editUserName: () -> Unit) {
    Button(
        onClick = { editUserName() }) {
        Text(text = "Modificar Nombre de Usuario")
    }
}

@Composable
fun ButtonModifyImage(editUserImage: () -> Unit) {
    Button(
        onClick = { editUserImage() }) {
        Text(text = "Modificar Foto de Perfil")
    }
}

@Composable
fun ButtonImage(viewModel: EditProfileViewModel) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OldPasswordField(oldPassword: String, oldPasswordError: Boolean, onTextFieldChanged: (String) -> Unit){
    OutlinedTextField(
        value = oldPassword, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Anterior Contraseña") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation()
    )
    if(oldPasswordError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Contraseña actual incorrecta")
    }
    Spacer(modifier = Modifier.padding(4.dp))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField1(password1: String, passwordError: Boolean, onTextFieldChanged: (String) -> Unit){
    OutlinedTextField(
        value = password1, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation()
    )
    if(passwordError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "La contraseña debe tener al menos 6 caracteres")
    }
    Spacer(modifier = Modifier.padding(4.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField2(password2: String, passwordRepError: Boolean, onTextFieldChanged: (String) -> Unit){
    OutlinedTextField(
        value = password2, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Repetir Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation()
    )
    if(passwordRepError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Las contraseñas no coinciden")
    }
}

@Composable
fun ButtonModifyPassword(editPassword: () -> Unit) {
    Button(
        onClick = { editPassword() }) {
        Text(text = "Cambiar contraseña")
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