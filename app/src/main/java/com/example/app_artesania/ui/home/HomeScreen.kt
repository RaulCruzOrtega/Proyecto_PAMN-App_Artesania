package com.example.app_artesania.ui.home

import android.annotation.SuppressLint

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme
import com.example.app_artesania.model.Category
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.defaultTopBar.DefaultTopBar

import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController){

    val categories: ArrayList<Category> = viewModel.categories.value!!
    val craftsmansDB by viewModel.craftsmansDB.observeAsState(ArrayList())
    val productsDB by viewModel.productsDB.observeAsState(ArrayList())
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)


    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            Scaffold (
                topBar = { DefaultTopBar(navController = navController) },
                bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(35.dp))
                    }
                    item(span = { GridItemSpan(2) }) { CategoriesSlider(categories, navController) }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item(span = { GridItemSpan(2) }) { CraftsmanSlider(craftsmansDB!!, navController) }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    item(span = { GridItemSpan(2) }) { ProductsSlider("Productos Nuevos", productsDB!!, navController) }
                    item(span = { GridItemSpan(2) }) { ProductsSlider("Productos en Tendencia", productsDB!!, navController) }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    item(span = { GridItemSpan(2) }) {
                        Text(
                            text = "Otros Productos",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    for (product in productsDB!!) {
                        item(span = { GridItemSpan(1) }) {ProductSmallViewTemplate(product, 180, navController) }
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
fun CategoriesSlider(categories: ArrayList<Category>, navController: NavController){
    LazyRow {
        itemsIndexed(categories) { index, item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .width(100.dp)
                    .clickable { navController.navigate(route = AppScreens.CategoryScreen.route + "/${item.categoryType.name}") }
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Icon(
                    painter = painterResource(id = categories[index].image),
                    contentDescription = "Categoría: $item",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = categories[index].categoryType.name,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun CraftsmanSlider(craftsmans: ArrayList<User>,navController: NavController){
    println("SE COLOCA LOS ARTESANOS")
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
            color = MaterialTheme.colorScheme.primary
        )
        LazyRow {
            itemsIndexed(craftsmans) { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(AppScreens.CraftsmanProfileScreen.route + "/${craftsmans[index].idCraftsman}")
                        }
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    var image: Any = item.image
                    if(item.image == ""){
                        image = "https://firebasestorage.googleapis.com/v0/b/app-artesania.appspot.com/o/usericon.png?alt=media&token=3b8d9258-3e22-49c8-ad51-47776f99f5a2"
                    }
                    Image(
                        painter = rememberImagePainter(data = image),
                        contentDescription = "Artesano $index: $item",
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = craftsmans[index].name,
                        color = MaterialTheme.colorScheme.primary,
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
fun ProductsSlider(title: String, products: ArrayList<Product>, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow {
            itemsIndexed(products) { _, product ->
                ProductSmallViewTemplate(product, 176, navController)
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