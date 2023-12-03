package com.example.app_artesania.data

import android.net.Uri
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.model.newOrder
import com.example.app_artesania.model.newProducto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

suspend fun getUserDoc(email: String): DocumentSnapshot? {
    return data.collection("Users")
        .whereEqualTo("email", email)
        .get().await().documents[0]
}

suspend fun newUser(newuser: User){
    val userHashMap = hashMapOf(
        "name" to newuser.name,
        "email" to newuser.email,
        "isCraftsman" to newuser.isCraftsman,
        "idCraftsman" to newuser.idCraftsman,
        "image" to newuser.image,
        "orders" to newuser.orders,
        "favoproducts" to newuser.favoproducts
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

suspend fun modifyUserName(userdata: User, newName: String){
    val user = getUser(userdata.email)
    val userdoc = getUserDoc(user.email)
    // Update the fields you want to modify
    val userHashMap = hashMapOf(
        "name" to newName,
        "email" to user.email,
        "isCraftsman" to user.isCraftsman,
        "idCraftsman" to user.idCraftsman,
        "image" to user.image,
        "orders" to user.orders,
        "favoproducts" to user.favoproducts
    )
    data.collection("Users").document(userdoc!!.id).update(userHashMap as Map<String, Any>).await()
}

suspend fun modifyUserImage(userdata: User, uri: Uri){
    val user = getUser(userdata.email)
    val userdoc = getUserDoc(user.email)

    // Update the fields you want to modify
    val userHashMap = hashMapOf(
        "name" to user.name,
        "email" to user.email,
        "isCraftsman" to user.isCraftsman,
        "idCraftsman" to user.idCraftsman,
        "image" to uri.toString(),
        "orders" to user.orders,
        "favoproducts" to user.favoproducts
    )
    data.collection("Users").document(userdoc!!.id).update(userHashMap as Map<String, Any>).await()
}

fun modifyUserFavo(emailUser: String, favoArray: ArrayList<String>){
    GlobalScope.launch(Dispatchers.IO) {
        val user = getUser(emailUser)
        val userdoc = getUserDoc(emailUser)

        val userHashMap = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "isCraftsman" to user.isCraftsman,
            "idCraftsman" to user.idCraftsman,
            "image" to user.image,
            "orders" to user.orders,
            "favoproducts" to favoArray
        )
        data.collection("Users").document(userdoc!!.id).update(userHashMap as Map<String, Any>).await()
    }
}

suspend fun modifyProduct(product: newProducto, idProduct: String){
    val productHashMap = hashMapOf(
        "name" to product.name,
        "idCraftsman" to product.idCraftsman,
        "image" to product.image,
        "price" to product.price,
        "category" to product.category,
        "description" to product.description
    )
    val userDocument = data.collection("Articulos").document(idProduct).update(productHashMap as Map<String, Any>).await()
}

suspend fun getProductsFilterByCategory(category: String): ArrayList<Product> {
    val productsList: ArrayList<Product> = ArrayList()
    val productCollection = data.collection("Articulos")
        .whereEqualTo("category", category).get().await()
    for (document in productCollection.documents) {
        val product = document.toObject<Product>()
        if (product != null) {
            product.id = document.id
            productsList.add(product)
        }
    }
    return productsList
}

suspend fun getAllOrders(excludeEmail: String): ArrayList<Order> {
    val ordersList: ArrayList<Order> = ArrayList()
    val ordersCollection = data.collection("Orders")
        .whereNotEqualTo("userEmail", excludeEmail).get().await()
    for (document in ordersCollection.documents) {
        val order = document.toObject<Order>()
        if (order != null) {
            order.id = document.id
            ordersList.add(order)
        }
    }
    return ordersList
}

suspend fun getOrdersByEmail(userEmail: String): ArrayList<Order> {
    val ordersList: ArrayList<Order> = ArrayList()
    val ordersCollection = data.collection("Orders")
        .whereEqualTo("userEmail", userEmail).get().await()
    for (document in ordersCollection.documents) {
        val order = document.toObject<Order>()
        if (order != null) {
            order.id = document.id
            ordersList.add(order)
        }
    }
    return ordersList
}

suspend fun getOrder(id: String): Order {
    val document = data.collection("Orders").document(id).get().await()
    val order = document.toObject<Order>()
    if (order != null){
        order.id = document.id
    }
    return order!!
}

suspend fun newOrderDB(newOrder: newOrder){
    val orderHashMap = hashMapOf(
        "title" to newOrder.title,
        "description" to newOrder.description,
        "category" to newOrder.category,
        "userEmail" to newOrder.userEmail,
        "offers" to newOrder.offers
    )
    data.collection("Orders").add(orderHashMap)
}

fun deleteOrder(orderId: String){
    data.collection("Orders").document(orderId).delete()
}

suspend fun modifyOrder(newOrder: Order){
    val orderHashMap = hashMapOf(
        "title" to newOrder.title,
        "description" to newOrder.description,
        "category" to newOrder.category,
        "userEmail" to newOrder.userEmail,
        "offers" to newOrder.offers
    )
    data.collection("Orders").document(newOrder.id).update(orderHashMap as Map<String, Any>).await()
}