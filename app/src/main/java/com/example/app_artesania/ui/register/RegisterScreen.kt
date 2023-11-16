package com.example.app_artesania.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.navigation.AppNavigation
import com.example.app_artesania.ui.login.HeaderImage
import com.example.app_artesania.ui.login.Login
import com.example.app_artesania.ui.login.LoginViewModel
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController) {
    Scaffold (topBar = {
        TopAppBar(
            title = {
                Text(text = "Registro", modifier = Modifier.padding(start = 20.dp))
            },
            navigationIcon = {
                Icon(
                    Icons.Default.ArrowBack,
                    "backIcon",
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .padding(start = 10.dp))
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        )
    }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp, top = 70.dp, 16.dp, 16.dp)
        ) {
            Register(viewModel, navController)
        }
    }

}
@Composable
fun Register(viewModel: RegisterViewModel, navController: NavController){
    val user_name: String by viewModel.user_name.observeAsState(initial = "")

    val email: String by viewModel.email.observeAsState(initial = "")
    val email_error: Boolean by viewModel.email_error.observeAsState(initial = false)

    val password: String by viewModel.password.observeAsState(initial = "")
    val password_rep: String by viewModel.password_rep.observeAsState(initial = "")
    val password_error: Boolean by viewModel.password_error.observeAsState(initial = false)
    val password_rep_error: Boolean by viewModel.password_rep_error.observeAsState(initial = false)


    val craftsman: Boolean by viewModel.craftsman.observeAsState(initial = false)
    val ID_craftsman: String by viewModel.ID_craftsman.observeAsState(initial = "")
    val craftsman_error: Boolean by viewModel.craftsman_error.observeAsState(initial = false)


    val RegisterEnable: Boolean by viewModel.RegisterEnable.observeAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()




    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {

            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            textfield(
                valor = user_name,
                placeholder = "Nombre de Usuario",
                onTextFieldChanged = { viewModel.onUserNameChanged(it) },
                keyboardtype = KeyboardType.Text,
                imeaction = ImeAction.Next
            )
            Spacer(modifier = Modifier.padding(4.dp))
            textfield(
                valor = email,
                placeholder = "Correo Electrónico",
                onTextFieldChanged = { viewModel.onEmailChanged(it) },
                keyboardtype = KeyboardType.Email,
                imeaction = ImeAction.Next
            )
            if(email_error){
                Spacer(modifier = Modifier.padding(4.dp))
                textError(texto = "Introduzca un correo electrónico válido")
            }
            Spacer(modifier = Modifier.padding(4.dp))
            textfield(
                valor = password,
                placeholder = "Contraseña",
                onTextFieldChanged = { viewModel.onPasswordChanged(it) },
                keyboardtype = KeyboardType.Password,
                imeaction = ImeAction.Next,
                visualTransformation = PasswordVisualTransformation()
            )
            if(password_error){
                Spacer(modifier = Modifier.padding(4.dp))
                textError(texto = "La contraseña debe tener al menos 6 caracteres")
            }
            Spacer(modifier = Modifier.padding(4.dp))
            textfield(
                valor = password_rep,
                placeholder = "Repetir Contraseña",
                onTextFieldChanged = { viewModel.onPassword_RepChanged(it) },
                keyboardtype = KeyboardType.Password,
                imeaction = ImeAction.Send,
                visualTransformation = PasswordVisualTransformation()
            )
            if(password_rep_error){
                Spacer(modifier = Modifier.padding(4.dp))
                textError(texto = "Las contraseñas no coinciden")
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row (modifier = Modifier
                .fillMaxSize()
                .size(48.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Text(text = "Eres Artesano ?",
                    textAlign = TextAlign.Center)
                Switch(checked = craftsman,
                    onCheckedChange = { viewModel.onSwitchChange() },
                    modifier = Modifier.padding(start = 20.dp))

            }
            if (craftsman == true) {
                Spacer(modifier = Modifier.padding(4.dp))
                textfield(
                    valor = ID_craftsman,
                    placeholder = "Numero Carnet Artesano",
                    onTextFieldChanged = { viewModel.onID_craftsmanChanged(it) },
                    keyboardtype = KeyboardType.Text,
                    imeaction = ImeAction.Send,
                    visualTransformation = PasswordVisualTransformation()
                )
                if(craftsman_error){
                    Spacer(modifier = Modifier.padding(4.dp))
                    textError(texto = "Número de Artesano Erroneo")
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
            RegisterButton ({
                coroutineScope.launch {
                    viewModel.onRegisterSelected(navController)
                }
            }
            )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textfield(valor: String,
              placeholder: String,
              onTextFieldChanged: (String) -> Unit,
              keyboardtype: KeyboardType,
              imeaction: ImeAction,
              visualTransformation: VisualTransformation = VisualTransformation.None) {
    TextField(
        value = valor, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = visualTransformation,
        placeholder = { Text(text = "${placeholder}") },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardtype,
            imeAction = imeaction),
        singleLine = true,
        maxLines = 1
    )
}
@Composable
fun RegisterButton(onRegisterSelected: () -> Unit) {
    Button(
        onClick = { onRegisterSelected() },
        colors = ButtonDefaults.buttonColors(Color(0, 139, 139)),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(text = "Registrarse", fontSize = 20.sp)
    }
}

@Composable
fun textError(
    texto: String
){
    Text(
        text = texto,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Red,
        textAlign = TextAlign.Start
        )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_ArtesaniaTheme {
        val navControler = rememberNavController()
        RegisterScreen(RegisterViewModel(), navControler)
    }
}