package com.example.app_artesania.ui.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.R
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.objetivo),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Mundo Canario",
                    color = Color.White
                    )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO: Manejar clic de búsqueda */ }) {

                Icon(
                    painter = painterResource(id = R.drawable.lupa),
                    contentDescription = "Search",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
            IconButton(onClick = { /* TODO: Manejar clic de configuración */ }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
        ,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        DefaultTopBar(navController)
    }
}