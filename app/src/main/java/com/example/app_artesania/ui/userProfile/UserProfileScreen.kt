package com.example.app_artesania.ui.userProfile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.R
import com.example.app_artesania.model.Product
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.craftsmanProfile.CraftsmanProfileViewModel
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel, navController: NavController) {
    val products: ArrayList<Product> = viewModel.products
    Scaffold (
        bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Top
        ) {
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item(span = { GridItemSpan(2) }) { profileHead() }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item(span = { GridItemSpan(2) }) { tabs(viewModel,navController) }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }
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
private fun profileHead() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(R.drawable.usericon),
            contentDescription = "UserProfile",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Nombre de Usuario", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "dirección de correo", fontSize = 16.sp)
    }
}

@Composable
private fun tabs(viewModel: UserProfileViewModel, navController: NavController) {
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
fun PreviewUserProfileBasicScreen() {
    App_ArtesaniaTheme {
        UserProfileScreen(UserProfileViewModel(), rememberNavController())
    }
}
