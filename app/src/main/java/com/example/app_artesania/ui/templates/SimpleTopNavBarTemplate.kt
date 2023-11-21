package com.example.app_artesania.ui.templates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopNavBar(title: String, navController: NavController){
    TopAppBar(
        title = {
            Text(text = title, modifier = Modifier.padding(start = 20.dp))
        },
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                "backIcon",
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(start = 10.dp))
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    )
}

