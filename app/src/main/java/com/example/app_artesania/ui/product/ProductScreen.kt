package com.example.app_artesania.ui.product

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.app_artesania.R
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel, navController: NavController) {
    val product by viewModel.product.observeAsState()
    val craftsman by viewModel.craftsman.observeAsState()
    val loadState by viewModel.loadState.observeAsState()
    val favo: Boolean by viewModel.favo.observeAsState(initial = false)

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            Scaffold(
                topBar = { SimpleTopNavBar(title = product!!.name, navController = navController) },
                bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(modifier = Modifier.padding(20.dp))
                        ProductDetailView(product!!, craftsman!!, viewModel, navController, favo)
                        Spacer(modifier = Modifier.padding(20.dp))
                    }
                }
            }
        }
        else -> {}
    }
}


@Composable
fun ProductDetailView(product: Product, craftsman: User, viewModel: ProductViewModel, navController: NavController, favo:Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(36.dp)
    ) {
        var image: Any = product.image
        if(product.image == ""){
            image = "https://firebasestorage.googleapis.com/v0/b/app-artesania.appspot.com/o/ImagenRota.jpg?alt=media&token=14b0a319-edd5-4a75-879c-3d6f1150b2de"
        }
        Image(
            painter = rememberImagePainter(data = image),
            contentDescription = "Producto ${product.name}",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(10))
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, end = 16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 25.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Categoría: " + product.category,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            Icon(
                painter = painterResource(R.drawable.hearticon),
                tint = if (favo) Color.Red else Color.LightGray,
                contentDescription = "favo",
                modifier = Modifier.size(25.dp)
                    .clickable { viewModel.favo() }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, end = 16.dp),
        ) {
            Column {
                Text(text = craftsman.name, fontSize = 18.sp)
                Text(text = product.idCraftsman, fontSize = 12.sp, modifier = Modifier.align(Alignment.End))
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = product.price.toString() + " €",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = product.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.secondary,
        )

        Spacer(modifier = Modifier.height(30.dp))
        if (DataRepository.getUser()!!.idCraftsman == craftsman.idCraftsman){
            tabs(viewModel, navController)
        } else {
            Button(onClick = { navController.navigate(route = AppScreens.BuyProductScreen.route) }) {
                Text(text = "Comprar")
            }
        }
    }
}

@Composable
private fun tabs(viewModel: ProductViewModel , navController: NavController) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { viewModel.editProduct(navController) }) {
            Text(text = "Editar Producto")
        }
        Button(onClick = { viewModel.delProduct(navController) }) {
            Text(text = "Eliminar Producto")
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        ProductScreen(ProductViewModel("1slFS2X3THA2jvLPSmcI", navController), navController)
    }
}