package com.example.app_artesania.data

import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.model.newProducto
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

suspend fun getCraftsman(idcraftsman: String): User{
    val usersList: ArrayList<User> = ArrayList()
    val users = data.collection("Users")
        .whereEqualTo("idCraftsman", idcraftsman)
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

suspend fun getProducts(): ArrayList<Product> {
    val productsList: ArrayList<Product> = ArrayList()
    val productCollection = data.collection("Articulos").get().await()
    for (document in productCollection.documents) {
        val product = document.toObject<Product>()
        if (product != null) {
            product.id = document.id
            productsList.add(product)
        }
    }
    return productsList
}

suspend fun getProduct(id: String): Product {
    val document = data.collection("Articulos").document(id).get().await()
    val product = document.toObject<Product>()
    if (product != null){
        product.id = document.id
    }
    return product!!
}

suspend fun getProducts_Craftsman(idcraftsman: String): ArrayList<Product> {
    val productsList: ArrayList<Product> = ArrayList()
    val productCollection = data.collection("Articulos")
        .whereEqualTo("idCraftsman", idcraftsman).get().await()
    for (document in productCollection.documents) {
        val product = document.toObject<Product>()
        if (product != null) {
            product.id = document.id
            productsList.add(product)
        }
    }
    return productsList
}

suspend fun newProduct(newProduct: newProducto){
    val productHashMap = hashMapOf(
        "name" to newProduct.name,
        "idCraftsman" to newProduct.idCraftsman,
        "image" to newProduct.image,
        "price" to newProduct.price,
        "category" to newProduct.category,
        "description" to newProduct.description
    )
    data.collection("Articulos").add(productHashMap)
}

fun deleteProduct(idProduct: String){
    data.collection("Articulos").document(idProduct).delete()
}
