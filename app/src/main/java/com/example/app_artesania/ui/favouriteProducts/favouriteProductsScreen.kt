package com.example.app_artesania.ui.favouriteProducts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.DefaultTopBar
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.templates.loader

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouriteProducts(viewModel: FavouriteProductsViewModel, navController: NavController) {
    val searchResults by viewModel.searchResults.observeAsState(arrayListOf())
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val favouriteProducts by viewModel.favoProducts.observeAsState(arrayListOf())
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
                            Spacer(modifier = Modifier.padding(45.dp))
                        }
                        item (span = { GridItemSpan(2) }) {
                            Text(
                            text = "Favoritos",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center)
                        }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                        for (product in favouriteProducts!!) {
                            item(span = { GridItemSpan(1) }) {
                                ProductSmallViewTemplate(
                                    product,
                                    180,
                                    navController
                                )
                            }
                        }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.padding(28.dp))
                        }
                    }
                }
            }
            else -> {
            }
        }
    }
}
