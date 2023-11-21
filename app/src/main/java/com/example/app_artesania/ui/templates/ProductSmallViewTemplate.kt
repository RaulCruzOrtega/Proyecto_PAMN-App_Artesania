package com.example.app_artesania.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_artesania.model.Product

@Composable
fun ProductSmallViewTemplate(product: Product, height: Int){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { println("Producto ${product.name}") }
    ) {
        Image(
            painter = painterResource(id = product.image as Int),
            contentDescription = "Producto ${product.name}",
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
                color = Color(android.graphics.Color.parseColor("#4c2c17")),
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.width(140.dp)
            )
            Text(
                text = product.price.toString() + " €",
                color = Color(android.graphics.Color.parseColor("#4a8cb0")),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.width(140.dp)
            )
        }
    }
}