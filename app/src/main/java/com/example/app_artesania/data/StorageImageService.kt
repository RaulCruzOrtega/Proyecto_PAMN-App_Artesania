package com.example.app_artesania.data

import android.media.Image
import android.net.Uri
import com.example.app_artesania.model.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

private val storage = FirebaseStorage.getInstance()

suspend fun addImageProductToFirebaseStorage(image: Uri, user: User): Uri{
    val downloadUrl = storage.reference.child("ImagenesProductos").child(user.email).child(image.lastPathSegment!!)
        .putFile(image).await()
        .storage.downloadUrl.await()

    return downloadUrl
}

suspend fun addImageUserToFirebaseStorage(image: Uri, user: User): Uri{
    val downloadUrl = storage.reference.child("ImagenesUsuarios").child(user.email).child(image.lastPathSegment!!)
        .putFile(image).await()
        .storage.downloadUrl.await()

    return downloadUrl
}