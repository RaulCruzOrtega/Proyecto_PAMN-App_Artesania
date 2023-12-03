package com.example.app_artesania.ui.infoCrafsman

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.User
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.DefaultTopBar
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.templates.ProfileImage
import com.example.app_artesania.ui.templates.loader


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCrafsmanScreen(viewModel: InfoCrafsmanViewModel, navController: NavController) {
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val products by viewModel.products.observeAsState()
    val craftsman by viewModel.craftsman.observeAsState()
    val searchResults by viewModel.searchResults.observeAsState()
    val isSearching = searchResults?.isNotEmpty() == true

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
                        searchResults?.forEach { product ->
                            item(span = { GridItemSpan(1) }) {
                                ProductSmallViewTemplate(product, 180, navController)
                            }
                        }
                    } else {
                        item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(35.dp))
                    }

                        item(span = { GridItemSpan(2) }) {
                            craftsman?.let { profileHead(it) } ?: Spacer(modifier = Modifier.height(40.dp))
                        }
                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        craftsman?.isCraftsman?.let { isCraftsman ->
                            if (isCraftsman && !products.isNullOrEmpty()) {
                                products!!.forEach { product ->
                                    item(span = { GridItemSpan(1) }) {
                                        ProductSmallViewTemplate(product, 180, navController)
                                    }
                                }
                            }
                        }
                    }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(28.dp))
                    }
                }
            }
            else -> {}
        }
    }
}


@Composable
private fun profileHead(craftsman: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ProfileImage(imageURL = craftsman.image, size = 150)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = craftsman.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = craftsman.email, fontSize = 16.sp)
    }
}





