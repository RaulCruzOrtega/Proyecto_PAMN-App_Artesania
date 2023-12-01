package com.example.app_artesania.ui.orders

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.templates.DefaultTopBar
import com.example.app_artesania.ui.templates.ProfileImage
import com.example.app_artesania.ui.templates.loader
import com.example.app_artesania.ui.theme.App_ArtesaniaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(viewModel: OrdersViewModel, navController: NavController) {
    val loadState by viewModel.loadState.observeAsState()
    val orders by viewModel.orders.observeAsState(ArrayList())
    val user by viewModel.user.observeAsState()
    val users by viewModel.users.observeAsState()

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            Scaffold(
                topBar = { DefaultTopBar(navController = navController) },
                bottomBar = { BottomNavBar(BottomNavBarViewModel(), navController) }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Spacer(modifier = Modifier.padding(40.dp)) }
                    item { Header(navController) }
                    item { Spacer(modifier = Modifier.padding(10.dp)) }
                    for (i in orders!!.indices) {
                        val order = orders!![i]
                        val currentUser = users!!.getOrNull(i)
                        item { OrderTemplate(order, currentUser, user!!, navController) }
                    }
                    item { Spacer(modifier = Modifier.padding(30.dp)) }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun Header(navController: NavController){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Mis pedidos", fontSize = 30.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Filled.Add,
            contentDescription = "Añadir Pedido",
            modifier = Modifier.clickable { navController.navigate(route = AppScreens.CreateOrderScreen.route) }
        )
    }
}


@Composable
fun OrderTemplate(order: Order, currentUser: User?, user: User, navController: NavController) {
    var actualUser: User? = currentUser
    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { /*TODO*/ }
        ) {
            if(actualUser == null){
                actualUser = user
            }
            ProfileImage(imageURL = actualUser!!.image, size = 75)
            Spacer(modifier = Modifier.padding(5.dp))
            Column (modifier = Modifier.weight(1f)){
                Text(text = order.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Categoría: " + order.category, fontSize = 15.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = order.description, fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.padding(5.dp))
            DeleteIcon(order)
        }
    }
}

@Composable
fun DeleteIcon(order: Order) {
    Icon(
        Icons.Filled.Delete,
        contentDescription = "Delete",
        modifier = Modifier.clickable { /* TODO */}
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    App_ArtesaniaTheme {
        val navController = rememberNavController()
        OrdersScreen(OrdersViewModel(), navController)
    }
}