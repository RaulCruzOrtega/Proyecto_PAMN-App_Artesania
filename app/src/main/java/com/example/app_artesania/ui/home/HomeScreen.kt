package com.example.app_artesania.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.model.Product
import com.example.app_artesania.ui.bottomNavBar.bottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val categories: ArrayList<HomeViewModel.Category> = viewModel.categories
    val products: ArrayList<Product> = viewModel.products
    val products2: ArrayList<Product> = viewModel.products2
    val craftsmans: ArrayList<HomeViewModel.Craftsman> = viewModel.craftsmans

    Scaffold (
        bottomBar = {
            bottomNavBar(BottomNavBarViewModel(), navController)
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            item(span = { GridItemSpan(2) }) { CategoriesSlider(categories) }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.padding(8.dp))
            }
            item(span = { GridItemSpan(2) }) { CraftsmanSlider(craftsmans) }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.padding(4.dp))
            }
            item(span = { GridItemSpan(2) }) { ProductsSlider("Productos Nuevos", products) }
            item(span = { GridItemSpan(2) }) { ProductsSlider("Productos en Tendencia", products2) }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.padding(4.dp))
            }
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Otros Productos",
                    color = Color(android.graphics.Color.parseColor("#4c2c17")),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            for (product in products) {
                item(span = { GridItemSpan(1) }) {ProductSmallViewTemplate(product, 180) }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.padding(28.dp))
            }
        }
    }
}

@Composable
fun CategoriesSlider(categories: ArrayList<HomeViewModel.Category>){
    LazyRow {
        itemsIndexed(categories) { index, item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .width(80.dp)
                    .clickable { println("Categoría $index: $item") }
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Image(
                    painter = painterResource(id = categories[index].img),
                    contentDescription = "Categoría: $item",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = categories[index].name,
                    color = Color(android.graphics.Color.parseColor("#4c2c17"))
                )
            }
        }
    }
}

@Composable
fun CraftsmanSlider(craftsmans: ArrayList<HomeViewModel.Craftsman>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Artesanos",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color(android.graphics.Color.parseColor("#4c2c17"))
        )
        LazyRow {
            itemsIndexed(craftsmans) { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { println("Artesano $index: $item") }
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Image(
                        painter = painterResource(id = craftsmans[index].img),
                        contentDescription = "Artesano $index: $item",
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = craftsmans[index].name,
                        color = Color(android.graphics.Color.parseColor("#4c2c17")),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(90.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductsSlider(title: String, products: ArrayList<Product>){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color(android.graphics.Color.parseColor("#4c2c17"))
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow {
            itemsIndexed(products) { _, product ->
                ProductSmallViewTemplate(product, 176)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        HomeScreen(HomeViewModel(), navController)
    }
}