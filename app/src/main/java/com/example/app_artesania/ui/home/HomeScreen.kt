package com.example.app_artesania.ui.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme
import com.example.app_artesania.model.Category
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.templates.DefaultTopBar
import com.example.app_artesania.ui.templates.ProfileImage
import com.example.app_artesania.ui.templates.loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val categories by viewModel.categories.observeAsState(arrayListOf())
    val craftsmansDB by viewModel.craftsmansDB.observeAsState(arrayListOf())
    val productsDB by viewModel.productsDB.observeAsState(arrayListOf())
    val newProducts by viewModel.newProducts.observeAsState(arrayListOf())
    val searchResults by viewModel.searchResults.observeAsState(arrayListOf())
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val isSearching = searchResults.isNotEmpty()

    Scaffold(
        topBar = {
            DefaultTopBar(navController = navController) { query ->
                if (query.isEmpty()) { viewModel.resetSearch() }
                else { viewModel.searchProducts(query) }
            }
        },
        bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
    ) {
        when (loadState) {
            LoadState.LOADING -> { loader() }
            LoadState.SUCCESS -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isSearching) {
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(35.dp))
                        }
                        items(searchResults.size) { index ->
                            ProductSmallViewTemplate(searchResults[index], 180, navController)
                        }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(28.dp))
                        }
                    } else {
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(35.dp))
                        }
                        item(span = { GridItemSpan(2) }) { CategoriesSlider(categories, navController) }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                        item(span = { GridItemSpan(2) }) { craftsmansDB?.let { it1 ->
                            CraftsmanSlider(
                                it1, navController)
                        } }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                        item(span = { GridItemSpan(2) }) { newProducts?.let { it1 ->
                            ProductsSlider("Productos Nuevos",
                                it1, navController)
                        } }
                        item(span = { GridItemSpan(2) }) {
                            Text(
                                text = "Otros Productos",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        productsDB?.forEach { product ->
                            item(span = { GridItemSpan(1) }) {
                                ProductSmallViewTemplate(product, 180, navController)
                            }
                        }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(28.dp))
                        }
                    }
                }
            }
            else -> { }
        }
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
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = categories[index].categoryType.name,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun CraftsmanSlider(craftsmans: ArrayList<User>,navController: NavController){
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
                    ProfileImage(item.image, 70)
                    Text(
                        text = craftsmans[index].name,
                        color = MaterialTheme.colorScheme.secondary,
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
            color = MaterialTheme.colorScheme.secondary
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