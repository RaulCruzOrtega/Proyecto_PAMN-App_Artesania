package com.example.app_artesania.ui.order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.ProfileImage
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(viewModel: OrderViewModel, navController: NavController) {
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val order by viewModel.order.observeAsState()
    val userOrder by viewModel.userOrder.observeAsState()
    val currentUser by viewModel.currentUser.observeAsState()

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            Scaffold(
                topBar = { SimpleTopNavBar(title = order!!.title, navController = navController) },
                bottomBar = {
                    BottomNavBar(BottomNavBarViewModel(), navController)
                }
            ) {
                OrderBody(order!!, userOrder!!, currentUser!!, viewModel, navController)
            }
        }
        else -> {}
    }
}

@Composable
fun OrderBody(order: Order, userOrder: User, currentUser: User, viewModel: OrderViewModel, navController: NavController){
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.padding(40.dp))
            OrderDetailView(order, userOrder)
            Spacer(modifier = Modifier.padding(10.dp))
        }
        if(currentUser.email == userOrder.email){
            item { Tabs(order, viewModel, navController) }
        }
    }
}

@Composable
fun OrderDetailView(order: Order, userOrder: User){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileImage(imageURL = userOrder.image, size = 75)
            Text(text = userOrder.name)
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Text(text = order.title, fontSize = 20.sp)
            Text(text = "Categoría: " + order.category)
        }
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
    Text(text = order.description, modifier = Modifier.padding(20.dp), textAlign = TextAlign.Justify)
    Divider(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp))
}

@Composable
fun Tabs(order: Order, viewModel: OrderViewModel, navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Editar Pedido")
        }
        Button(onClick = {
            viewModel.delOrder(navController)
        }) {
            Text(text = "Eliminar Pedido")
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}