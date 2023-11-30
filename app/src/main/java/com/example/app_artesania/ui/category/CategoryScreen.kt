package com.example.app_artesania.ui.category

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.ProductSmallViewTemplate
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(viewModel: CategoryViewModel, navController: NavController){
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val products by viewModel.products.observeAsState(ArrayList())
    val category = viewModel.category

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            Scaffold(
                topBar = { SimpleTopNavBar(title = "Sección de $category", navController = navController) },
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
                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    for (product in products!!) {
                        item(span = { GridItemSpan(1) }) { ProductSmallViewTemplate(product, 180, navController) }
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