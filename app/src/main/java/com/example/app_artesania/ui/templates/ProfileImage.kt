package com.example.app_artesania.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ProfileImage(imageURL: String, size: Int) {
    var image: String = imageURL
    if(imageURL == ""){
        image = "https://firebasestorage.googleapis.com/v0/b/app-artesania.appspot.com/o/usericon.png?alt=media&token=3b8d9258-3e22-49c8-ad51-47776f99f5a2"
    }
    Image(
        painter = rememberImagePainter(data = image),
        contentDescription = "UserProfile",
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    )
}