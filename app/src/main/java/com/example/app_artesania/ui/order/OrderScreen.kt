package com.example.app_artesania.ui.order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.ProfileImage
import com.example.app_artesania.ui.templates.SimpleTopNavBar
import com.example.app_artesania.ui.templates.loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(viewModel: OrderViewModel, navController: NavController) {
    val loadState by viewModel.loadState.observeAsState(LoadState.LOADING)
    val order by viewModel.order.observeAsState()

    when (loadState) {
        LoadState.LOADING -> { loader() }
        LoadState.SUCCESS -> {
            Scaffold(
                topBar = { SimpleTopNavBar(title = order!!.title, navController = navController) },
                bottomBar = {
                    BottomNavBar(BottomNavBarViewModel(), navController)
                }
            ) {
                OrderBody(viewModel, navController)
            }
        }
        else -> {}
    }
}

@Composable
fun OrderBody(viewModel: OrderViewModel, navController: NavController){
    val userOrder by viewModel.userOrder.observeAsState()
    val currentUser by viewModel.currentUser.observeAsState()
    val order by viewModel.order.observeAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.padding(40.dp))
            OrderDetailView(order!!, userOrder!!)
            Spacer(modifier = Modifier.padding(10.dp))
        }
        if(currentUser!!.email == userOrder!!.email){
            item { Tabs(viewModel, navController) }
            item { ViewOffers(order!!) }
        }
        else {
            item { MakeOffer(viewModel, navController) }
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
fun Tabs(viewModel: OrderViewModel, navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { viewModel.editOrder(navController) }) {
            Text(text = "Editar Pedido")
        }
        Button(onClick = { viewModel.delOrder(navController) }) {
            Text(text = "Eliminar Pedido")
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun ViewOffers(order: Order){
    Column (
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        for(offer in order.offers){
            Text(text = offer.idCraftsman)
            Text(text = offer.price.toString())
            Text(text = offer.comment)
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun MakeOffer(viewModel: OrderViewModel, navController: NavController){
    val offerPrice by viewModel.offerPrice.observeAsState(initial = "")
    val offerComments by viewModel.offerComments.observeAsState(initial = "")
    val offerCommentsError by viewModel.offerCommentsError.observeAsState(initial = false)
    val offerPriceError by viewModel.offerPriceError.observeAsState(initial = false)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ){
        CommentsField(offerComments!!, offerCommentsError!!) { viewModel.onEditOfferChanged(offerPrice!!, it) }
        Spacer(modifier = Modifier.padding(8.dp))
        PriceField(offerPrice!!, offerPriceError!!) { viewModel.onEditOfferChanged(it, offerComments!!) }
        Spacer(modifier = Modifier.padding(8.dp))
        ButtonMakeOffer(viewModel, navController)
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceField(price: String, priceError: Boolean ,onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = price, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        label = { Text(text = "Precio de la oferta (€)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1
    )
    if(priceError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Introduzca el precio de la oferta")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsField(comments: String, commentsError: Boolean ,onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = comments, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        label = { Text(text = "Comentarios de la oferta") },
        maxLines = 10
    )
    if(commentsError){
        Spacer(modifier = Modifier.padding(4.dp))
        textError(texto = "Introduzca comentarios de la oferta")
    }
}

@Composable
fun ButtonMakeOffer(viewModel: OrderViewModel, navController: NavController){
    Button(onClick = { viewModel.makeOffer(navController) }) {
        Text(text = "Hacer Oferta")
    }
    Spacer(modifier = Modifier.padding(8.dp))
}