package com.example.app_artesania.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.app_artesania.model.Product
import com.example.app_artesania.navigation.AppScreens

@Composable
fun ProductSmallViewTemplate(product: Product, height: Int, navController: NavController){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            navController.navigate(route = AppScreens.ProductScreen.route + "/${product.id}")
        }
    ) {
        var image: Any = product.image
        if(product.image == ""){
            image = "https://firebasestorage.googleapis.com/v0/b/app-artesania.appspot.com/o/ImagenRota.jpg?alt=media&token=14b0a319-edd5-4a75-879c-3d6f1150b2de"
        }
        Image(
            painter = rememberImagePainter(data = image),
            contentDescription = "Producto ${product.name}",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .height(height.dp)
                .width(170.dp)
                .padding(start=0.dp, end = 10.dp)
                .clip(RoundedCornerShape(10))
        )
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .height(70.dp)
        ) {
            Text(
                text = product.name,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.width(140.dp)
            )
            Text(
                text = product.price.toString() + " €",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.width(140.dp)
            )
        }
    }
}