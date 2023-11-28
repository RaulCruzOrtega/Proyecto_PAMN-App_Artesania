package com.example.app_artesania.data

import android.media.Image
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

private val storage = FirebaseStorage.getInstance()

suspend fun addImageProductToFirebaseStorage(image: Uri): Uri{
    val downloadUrl = storage.reference.child("ImagenesProductos").child(image.lastPathSegment!!)
        .putFile(image).await()
        .storage.downloadUrl.await()

    return downloadUrl
}