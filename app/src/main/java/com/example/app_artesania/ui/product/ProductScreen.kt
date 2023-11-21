package com.example.app_artesania.ui.product

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.app_artesania.model.Product
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel, navController: NavController) {
    val product: Product = viewModel.product

    Scaffold (
        topBar = { SimpleTopNavBar(title = product.name, navController = navController) },
        bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                Spacer(modifier = Modifier.padding(20.dp))
                ProductDetailView(product)
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}


@Composable
fun ProductDetailView(product: Product) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(36.dp)
    ) {
        Text(
            text = product.name,
            fontSize = 20.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(10.dp))
        
        Image(
            painter = painterResource(id = product.image as Int),
            contentDescription = "Producto ${product.name}",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(10))
        )
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp, end = 16.dp),
        ) {
            Text(
                text = product.idCraftsman,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = product.price.toString() + " €",
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = product.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { /* TODO: Acción del botón */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comprar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        ProductScreen(ProductViewModel("1"), navController)
    }
}