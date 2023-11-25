package com.example.app_artesania.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.currentUser
import com.example.app_artesania.data.currentUserEmail
import com.example.app_artesania.data.getUser
import com.example.app_artesania.data.notexistUser
import com.example.app_artesania.data.signIn
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.launch


class LoginViewModel(navController: NavController) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _errorData = MutableLiveData<Boolean>()
    val errorData: LiveData<Boolean> = _errorData

    private val _errorEmail = MutableLiveData<Boolean>()
    val errorEmail: LiveData<Boolean> = _errorEmail

    init {
        val login = currentUser()
        if (login){
            current_User_exist(navController)
        }
    }

    fun current_User_exist(navController: NavController){
        viewModelScope.launch {
            val current_email = currentUserEmail()
            val usuario = getUser(current_email!!)
            DataRepository.setUser(usuario)
            if (navController.currentDestination?.route != AppScreens.HomeScreen.route) {
                navController.navigate(route = AppScreens.HomeScreen.route)
            }
        }
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
        _errorData.value = false
        _errorEmail.value = false
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()


    suspend fun onLoginSelected(navController: NavController) {

        val loginCorrect = signIn(email.value!!, password.value!!)
        val no_exist_user = notexistUser(email.value!!)
        if (!no_exist_user) {
            if (loginCorrect) {
                val usuario = getUser(email.value!!)
                DataRepository.setUser(usuario)
                if (navController.currentDestination?.route != AppScreens.HomeScreen.route) {
                    navController.navigate(route = AppScreens.HomeScreen.route)
                }
            } else {
                _errorData.value = true
            }
        }
        else{
            _errorEmail.value = true
        }

    }

    suspend fun onRegisterScreen(navController: NavController) {
        navController.navigate(route = AppScreens.RegisterScreen.route)
    }


}