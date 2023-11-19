package com.example.app_artesania.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private val data = FirebaseFirestore.getInstance()

suspend fun notexistUser(email:String): Boolean{
    val exist = data.collection("Usuarios")
        .whereEqualTo("email", email)
        .get().await().isEmpty
    return exist
}