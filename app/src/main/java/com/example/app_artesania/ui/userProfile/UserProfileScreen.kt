package com.example.app_artesania.ui.userProfile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.DefaultTopBar
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel, navController: NavController) {
    val loadState by viewModel.loadState.observeAsState()
    val products by viewModel.products.observeAsState()
    val user by viewModel.user.observeAsState()

    when (loadState) {
        LoadState.LOADING -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            ) {
                // Muestra el círculo de carga
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                    color = Color.Black,
                )
            }
            println("Esperando")
        }

        LoadState.SUCCESS -> {
            Scaffold(
                topBar = { DefaultTopBar(navController = navController) },
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
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                    item(span = { GridItemSpan(2) }) { profileHead(user!!) }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    item(span = { GridItemSpan(2) }) { tabs(viewModel, navController) }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    if (DataRepository.getUser()!!.isCraftsman) {
                        for (product in products!!) {
                            item(span = { GridItemSpan(1) }) {
                                ProductSmallViewTemplate(
                                    product,
                                    180,
                                    navController
                                )
                            }
                        }
                    }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(28.dp))
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
private fun profileHead(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        var image: Any = user.image
        if(user.image == ""){
            image = "https://firebasestorage.googleapis.com/v0/b/app-artesania.appspot.com/o/usericon.png?alt=media&token=3b8d9258-3e22-49c8-ad51-47776f99f5a2"
        }
        Image(
            painter = rememberImagePainter(data = image),
            contentDescription = "UserProfile",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = user.email, fontSize = 16.sp)
    }
}

@Composable
private fun tabs(viewModel: UserProfileViewModel, navController: NavController) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { navController.navigate(route = AppScreens.EditProfileScreen.route) }) {
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
