package com.example.app_artesania.data

import com.example.app_artesania.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

private val data = FirebaseFirestore.getInstance()

suspend fun notexistUser(email:String): Boolean{
    val exist = data.collection("Users")
        .whereEqualTo("email", email)
        .get().await().isEmpty
    return exist
}

suspend fun getUser(email: String): User{
    val usersList: ArrayList<User> = ArrayList()
    val users = data.collection("Users")
        .whereEqualTo("email", email)
        .get().await()
    for (document in users.documents) {
        val user = document.toObject<User>()
        if (user != null) {
            user.isCraftsman = document.getBoolean("isCraftsman")!!
            usersList.add(user)
        }
    }
    return usersList[0]

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
    data.collection("Users").add(userHashMap)
}

suspend fun getUsers(): ArrayList<User> {
    val usersList: ArrayList<User> = ArrayList()
    val userCollection = data.collection("Users").get().await()
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
    val userCollection = data.collection("Users").whereEqualTo("isCraftsman", true).get().await()
    for (document in userCollection.documents) {
        val user = document.toObject<User>()
        if (user != null) {
            user.isCraftsman = true
            craftsmansList.add(user)
        }
    }
    println("SE HA EJECUTADO")
    return craftsmansList
}