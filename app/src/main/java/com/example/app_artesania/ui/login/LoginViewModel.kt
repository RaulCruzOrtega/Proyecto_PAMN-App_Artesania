package com.example.app_artesania.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_artesania.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _errorData = MutableLiveData<Boolean>()
    val errorData: LiveData<Boolean> = _errorData

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
        _errorData.value = false
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    suspend fun onLoginSelected(navController: NavController) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    navController.navigate(route = AppScreens.HomeScreen.route)
                } else {
                    _errorData.value = true
                }
            }
    }

    suspend fun onRegisterScreen(navController: NavController) {
        navController.navigate(route = AppScreens.HomeScreen.route)
    }


}