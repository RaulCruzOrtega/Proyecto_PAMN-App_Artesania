package com.example.app_artesania.ui.defaultTopBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.R
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(navController: NavController) {
    val showSearchBar = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    TopAppBar(
        modifier = Modifier.height(65.dp),
        title = {
            if (!showSearchBar.value) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.objetivo),
                            contentDescription = "App Icon",
                            modifier = Modifier.size(35.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = "Mundo Canario",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
        },

        actions = {
            if (showSearchBar.value) {
                Column {
                    TextField(
                        value = searchText.value,
                        onValueChange = { newText -> searchText.value = newText },
                        placeholder = { Text("Buscar...") },
                        singleLine = true,

                        trailingIcon = {
                            if (searchText.value.isNotEmpty()) {
                                IconButton(onClick = { searchText.value = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        }
                    )
                }
                IconButton(onClick = { showSearchBar.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Close Search", tint=Color.White)
                }
            } else {

                IconButton(onClick = { showSearchBar.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.lupa),
                        contentDescription = "Search",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }

            }
        },
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

