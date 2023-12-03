package com.example.app_artesania.ui.userProfile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.DefaultTopBar
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.templates.ProfileImage
import com.example.app_artesania.ui.templates.loader
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel, navController: NavController) {
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val products by viewModel.products.observeAsState(arrayListOf())
    val user by viewModel.user.observeAsState()
    val searchResults by viewModel.searchResults.observeAsState(arrayListOf())
    val isSearching = searchResults.isNotEmpty()

    Scaffold(
        topBar = {
            DefaultTopBar(navController = navController) { query ->
                if (query.isEmpty()) {
                    // Si la búsqueda se ha borrado, resetear los resultados de búsqueda
                    viewModel.resetSearch()
                } else {
                    viewModel.searchProducts(query)
                }
            }
        },
        bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
    ) {
        when (loadState) {
            LoadState.LOADING -> { loader() }
            LoadState.SUCCESS -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Top
                ) {
                    if (isSearching) {
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(35.dp))
                        }
                        items(searchResults.size) { index ->
                            ProductSmallViewTemplate(
                                searchResults[index],
                                180,
                                navController
                            )
                        }
                    } else {
                        // Mostrar información del usuario solo si no hay búsqueda activa
                        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(40.dp)) }
                        item(span = { GridItemSpan(2) }) { user?.let { profileHead(it) } }
                        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(20.dp)) }
                        item(span = { GridItemSpan(2) }) { tabs(viewModel, navController) }
                        item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(20.dp)) }

                        if (user?.isCraftsman == true) {
                            products?.forEach { product ->
                                item(span = { GridItemSpan(1) }) {
                                    ProductSmallViewTemplate(
                                        product,
                                        180,
                                        navController
                                    )
                                }
                            }
                        }
                    }
                    item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.padding(28.dp)) }
                }
            }
            else -> {
                // Aquí puedes manejar otros estados como errores
            }
        }
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
        ProfileImage(imageURL = user.image, size = 150)
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
