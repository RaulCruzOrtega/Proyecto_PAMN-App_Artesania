package com.example.app_artesania.data

import com.example.app_artesania.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private val data = FirebaseFirestore.getInstance()

suspend fun notexistUser(email:String): Boolean{
    val exist = data.collection("Usuarios")
        .whereEqualTo("email", email)
        .get().await().isEmpty
    return exist
}

suspend fun newUser(newuser: User){
    val userHashMap = hashMapOf(
        "name" to newuser.name,
        "email" to newuser.email,
        "isCraftsman" to newuser.isCraftsman,
        "idCraftsman" to newuser.idCraftsman,
        "image" to newuser.image,
        "orders" to newuser.orders,
        "products" to newuser.products
    )
    data.collection("Usuarios").add(userHashMap)
}