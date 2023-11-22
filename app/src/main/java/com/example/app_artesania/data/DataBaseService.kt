package com.example.app_artesania.data

import com.example.app_artesania.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
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

suspend fun getUsers(): ArrayList<User> {
    val usersList: ArrayList<User> = ArrayList()
    val userCollection = data.collection("Usuarios").get().await()
    for (document in userCollection.documents) {
        val user = document.toObject<User>()
        if (user != null) {
            user.isCraftsman = document.getBoolean("isCraftsman")!!
            usersList.add(user)
        }
    }
    return usersList
}

suspend fun getCraftsmans(): ArrayList<User> {
    val craftsmansList: ArrayList<User> = ArrayList()
    val userCollection = data.collection("Usuarios").get().await()
    for (document in userCollection.documents) {
        val isCraftsman = document.getBoolean("isCraftsman")!!
        val user = document.toObject<User>()
        if (user != null && isCraftsman) {
            user.isCraftsman = true
            craftsmansList.add(user)
        }
    }
    return craftsmansList
}