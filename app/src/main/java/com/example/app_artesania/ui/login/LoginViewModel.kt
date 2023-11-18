package com.example.app_artesania.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_artesania.data.signIn
import com.example.app_artesania.navigation.AppScreens


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
        val loginCorrect = signIn(email.value!!, password.value!!)
        if (loginCorrect) {
            navController.navigate(route = AppScreens.HomeScreen.route)
        } else {
            _errorData.value = true
        }

    }

    suspend fun onRegisterScreen(navController: NavController) {
        navController.navigate(route = AppScreens.RegisterScreen.route)
    }


}