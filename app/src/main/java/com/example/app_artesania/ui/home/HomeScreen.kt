package com.example.app_artesania.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme


@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val categories: ArrayList<HomeViewModel.Category> = viewModel.categories
    val products: ArrayList<HomeViewModel.Product> = viewModel.products
    val products2: ArrayList<HomeViewModel.Product> = viewModel.products2
    val craftsmans: ArrayList<HomeViewModel.Craftsman> = viewModel.craftsmans

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(8.dp))
                CategoriesSlider(categories)
                Spacer(modifier = Modifier.padding(8.dp))
                CraftsmanSlider(craftsmans)
                Spacer(modifier = Modifier.padding(14.dp))
                ProductsSlider("Productos Nuevos", products)
                Spacer(modifier = Modifier.padding(4.dp))
                ProductsSlider("Productos en Tendencia", products2)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun CategoriesSlider(categories: ArrayList<HomeViewModel.Category>){
    LazyRow {
        itemsIndexed(categories) { index, item ->
            Column(
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
                    color = Color(android.graphics.Color.parseColor("#4c2c17")),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CraftsmanSlider(craftsmans: ArrayList<HomeViewModel.Craftsman>){
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

@Composable
fun ProductsSlider(title: String, products: ArrayList<HomeViewModel.Product>){
    Text(
        text = title,
        fontSize = 20.sp,
        color = Color(android.graphics.Color.parseColor("#4c2c17"))
    )
    Spacer(modifier = Modifier.height(5.dp))
    LazyRow {
        itemsIndexed(products) { index, item ->
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.clickable { println("Producto $index: $item") }
            ) {
                Image(
                    painter = painterResource(id = products[index].img),
                    contentDescription = "Producto $index: $item",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(170.dp)
                        .width(170.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10))
                )
                Column(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(70.dp)
                ) {
                    Text(
                        text = products[index].name,
                        color = Color(android.graphics.Color.parseColor("#4c2c17")),
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(140.dp)
                    )
                    Text(
                        text = products[index].price.toString() + " €",
                        color = Color(android.graphics.Color.parseColor("#4a8cb0")),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(140.dp)
                    )
                }
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