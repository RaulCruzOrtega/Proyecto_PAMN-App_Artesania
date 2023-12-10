package com.example.app_artesania.ui.bottomNavBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.model.ItemsBottomNav
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@Composable
fun BottomNavBar(viewModel: BottomNavBarViewModel, navController: NavController){
    val items: List<ItemsBottomNav> by viewModel.items.observeAsState(ArrayList())

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            items.forEach {item->
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(route = item.route) },
                    icon = { Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title,
                        tint = Color.White,
                        modifier = Modifier.height(30.dp))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        BottomNavBar(BottomNavBarViewModel(), navController)
    }
}