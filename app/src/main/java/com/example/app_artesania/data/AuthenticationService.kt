package com.example.app_artesania.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await


private val auth = FirebaseAuth.getInstance()

suspend fun createUser(email: String, password: String): Boolean{
    return try {
        val authResultRegister = CompletableDeferred<Boolean>()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    authResultRegister.complete(true)
                } else {
                    authResultRegister.complete(false)
                }
            }
        authResultRegister.await()
    } catch (e: Exception){
        false
    }
}

suspend fun signIn(email: String, password: String): Boolean {
    return try {
        val authResultSingIn = CompletableDeferred<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    authResultSingIn.complete(true)
                } else {
                    authResultSingIn.complete(false)
                }
            }
        authResultSingIn.await()
    } catch (e: Exception){
        false
    }
}

fun currentUser(): Boolean{
    return auth.currentUser != null
}

fun currentUserEmail(): String?{
    return auth.currentUser?.email
}

fun SingOut(){
    auth.signOut()
}

suspend fun changePassword(oldPassword: String, newPassword: String) {
    val user = auth.currentUser!!
    val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword) // Reemplaza "old_password" con la contraseña actual del usuario
    user.reauthenticate(credential).await()
    user.updatePassword(newPassword).await()
}
