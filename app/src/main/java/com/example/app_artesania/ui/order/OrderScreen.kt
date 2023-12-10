package com.example.app_artesania.ui.order

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Offer
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import com.example.app_artesania.ui.bottomNavBar.BottomNavBar
import com.example.app_artesania.ui.bottomNavBar.BottomNavBarViewModel
import com.example.app_artesania.ui.register.textError
import com.example.app_artesania.ui.templates.DeleteConfirmationDialog
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
    val editingOffer by viewModel.editingOffer.observeAsState(initial = false)

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
            item { Tabs(order!!.isAssigned, viewModel, navController) }
            item { Spacer(modifier = Modifier.padding(10.dp)) }
            item { Text(text = "Ofertas de artesanos", fontSize = 20.sp) }
            if(order!!.offers.isEmpty()){
                item { Text(
                    text = "Todavía no han hecho ninguna oferta a tu pedido.",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) }
            }
            else{
                for(offer in order!!.offers) {
                    item { ShowOffer(order!!, offer, false, viewModel, navController) }
                }
            }
        }
        else {
            val matchingOffer = order!!.offers.find { it.idCraftsman == currentUser?.idCraftsman }
            println("otra $matchingOffer")
            if (matchingOffer != null) {
                if (editingOffer){
                    item { EditOffer(matchingOffer, viewModel, navController) }
                }
                else{
                    item { ShowOffer(order!!, matchingOffer, true, viewModel, navController) }
                }
            }
            else{
                item { MakeOffer(viewModel, navController) }
            }
        }
        item { Spacer(modifier = Modifier.padding(35.dp)) }
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
    if(order.image != ""){
        Image(
            painter = rememberImagePainter(order.image),
            contentDescription = "Order ${order.title}",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(10))
        )
        Spacer(modifier = Modifier.padding(10.dp))
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
fun Tabs(isAssigned: Boolean, viewModel: OrderViewModel, navController: NavController){
    val order by viewModel.order.observeAsState()
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            "¿Está seguro de que desea eliminar el Pedido con título \"${order!!.title}\"?"
        ) { confirmed ->
            if (confirmed) { viewModel.delOrder(navController) }
            showDialog = false
        }
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if(!isAssigned) {
            Button(onClick = { viewModel.editOrder(navController) }) {
                Text(text = "Editar Pedido")
            }
        }
        Button(onClick = { showDialog = true }) {
            Text(text = "Eliminar Pedido")
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun ShowOffer(order: Order, offer: Offer, myOffer: Boolean, viewModel: OrderViewModel, navController: NavController){
    println(offer)
    val craftsmans by viewModel.offeredCraftsmans.observeAsState()
    val craftsman: User = craftsmans!!.find { it.idCraftsman == offer.idCraftsman }!!

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
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ProfileImage(imageURL = craftsman.image, size = 75)
                Text(text = craftsman.name, fontSize = 15.sp, fontWeight = FontWeight.Light)
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = offer.price.toString() + " €",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = offer.comment, fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.padding(5.dp))
            if (myOffer && !order.isAssigned) {
                IconEditOffer(offer, viewModel)
                IconDeleteOffer(order, craftsman.idCraftsman, viewModel, navController)
            }
            else if (!order.isAssigned){
                IconAcceptOffer(order, craftsman.idCraftsman, viewModel, navController)
            }
        }
    }
}

@Composable
fun MakeOffer(viewModel: OrderViewModel, navController: NavController){
    val offerPrice by viewModel.offerPrice.observeAsState(initial = "")
    val offerComments by viewModel.offerComments.observeAsState(initial = "")
    val offerCommentsError by viewModel.offerCommentsError.observeAsState(initial = false)
    val offerPriceError by viewModel.offerPriceError.observeAsState(initial = false)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
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

@Composable
fun IconEditOffer(offer: Offer, viewModel: OrderViewModel){
    Icon(
        Icons.Filled.Edit,
        contentDescription = "Edit Offer",
        modifier = Modifier.clickable { viewModel.isEditingOffer(offer) }
    )
}
@Composable
fun IconDeleteOffer(order: Order, idCraftsman: String, viewModel: OrderViewModel, navController: NavController){
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            "¿Está seguro de que desea eliminar su oferta?"
        ) { confirmed ->
            if (confirmed) { viewModel.deleteOffer(order, idCraftsman, navController) }
            showDialog = false
        }
    }

    Icon(
        Icons.Filled.Delete,
        contentDescription = "Delete Offer",
        modifier = Modifier.clickable { showDialog = true}
    )
}

@Composable
fun IconAcceptOffer(order: Order, idCraftsman: String, viewModel: OrderViewModel, navController: NavController){
    Icon(
        Icons.Filled.CheckCircle,
        contentDescription = "Accept Offer",
        modifier = Modifier.clickable { viewModel.acceptOffer(order, idCraftsman, navController)}
    )
}

@Composable
fun EditOffer(offer: Offer, viewModel: OrderViewModel, navController: NavController){
    val offerPrice by viewModel.offerPrice.observeAsState(initial = "")
    val offerComments by viewModel.offerComments.observeAsState(initial = "")
    val offerCommentsError by viewModel.offerCommentsError.observeAsState(initial = false)
    val offerPriceError by viewModel.offerPriceError.observeAsState(initial = false)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        CommentsField(offerComments!!, offerCommentsError!!) { viewModel.onEditOfferChanged(offerPrice!!, it) }
        Spacer(modifier = Modifier.padding(8.dp))
        PriceField(offerPrice!!, offerPriceError!!) { viewModel.onEditOfferChanged(it, offerComments!!) }
        Spacer(modifier = Modifier.padding(8.dp))
        ButtonEditOffer(offer, viewModel, navController)
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun ButtonEditOffer(offer: Offer, viewModel: OrderViewModel, navController: NavController){
    val order by viewModel.order.observeAsState()
    println(offer.idCraftsman)
    Button(onClick = { viewModel.editOffer(order!!, offer.idCraftsman, navController) }) {
        Text(text = "Editar Oferta")
    }
    Spacer(modifier = Modifier.padding(8.dp))
}