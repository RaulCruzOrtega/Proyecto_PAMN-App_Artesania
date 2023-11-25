package com.example.app_artesania.ui.craftsmanProfile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.R
import com.example.app_artesania.model.Product
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CraftsmanProfileScreen(viewModel: CraftsmanProfileViewModel, navController: NavController) {
    val products: ArrayList<Product> = viewModel.products

    Scaffold (
        bottomBar = {
            BottomNavBar(BottomNavBarViewModel(), navController)
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            item(span = { GridItemSpan(2) }) { ProfileHead() }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.padding(4.dp))
            }
            item(span = { GridItemSpan(2) }) { Tabs(viewModel, navController) }
            for (product in products) {
                item(span = { GridItemSpan(1) }) { ProductSmallViewTemplate(product, 180, navController) }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.padding(28.dp))
            }
        }
    }
}

@Composable
private fun ProfileHead(){
    Surface(color = Color(android.graphics.Color.parseColor("#4c2c17"))) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.usericon),
                contentDescription = "UserProfile",
                tint = Color.White,
                modifier = Modifier
                    .width(78.dp)
                    .height(78.dp)
                    .clip(shape = CircleShape)
                    .padding(8.dp)
                    .border(border = BorderStroke(1.dp, color = Color.White), shape = CircleShape)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "User", color = Color.White, fontSize = 24.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "UserInfo", color = Color.White, fontSize = 12.sp)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun Tabs(viewModel: CraftsmanProfileViewModel, navController: NavController) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Editar Perfil")
        }
        Button(onClick = { viewModel.cerrarSesión(navController) }) {
            Text(text = "Cerrar Sesión")
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        CraftsmanProfileScreen(CraftsmanProfileViewModel(), navController)
    }
}