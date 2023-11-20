package com.example.app_artesania.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_artesania.data.createUser
import com.example.app_artesania.data.newUser
import com.example.app_artesania.data.notexistUser
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens


class RegisterViewModel: ViewModel() {
    private val _user_name = MutableLiveData<String>().apply{value = ""}
    val user_name: LiveData<String> = _user_name

    private val _user_name_error = MutableLiveData<Boolean>().apply { value = false }
    val user_name_error: LiveData<Boolean> = _user_name_error

    private val _email = MutableLiveData<String>().apply{value = ""}
    val email: LiveData<String> = _email

    private val _email_error = MutableLiveData<Boolean>().apply { value = false }
    val email_error: LiveData<Boolean> = _email_error

    private val _password = MutableLiveData<String>().apply{value = ""}
    val password: LiveData<String> = _password

    private val _password_rep = MutableLiveData<String>().apply{value = ""}
    val password_rep: LiveData<String> = _password_rep

    private val _password_error = MutableLiveData<Boolean>().apply { value = false }
    val password_error: LiveData<Boolean> = _password_error

    private val _password_rep_error = MutableLiveData<Boolean>().apply { value = false }
    val password_rep_error: LiveData<Boolean> = _password_rep_error

    private val _craftsman = MutableLiveData<Boolean>().apply { value = false }
    val craftsman: LiveData<Boolean> = _craftsman

    private val _ID_craftsman = MutableLiveData<String>().apply{value = ""}
    val ID_craftsman: LiveData<String> = _ID_craftsman

    private val _craftsman_error = MutableLiveData<Boolean>().apply { value = false }
    val craftsman_error: LiveData<Boolean> = _craftsman_error

    private val _ExistUser = MutableLiveData<Boolean>().apply { value = false }
    val ExistUser: LiveData<Boolean> = _ExistUser


    fun onSwitchChange(){
        _craftsman.value = !_craftsman.value!!
        _craftsman_error.value = false
    }

    fun onUserNameChanged(username: String) {
        _user_name.value = username
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        _email_error.value = false
        _ExistUser.value = false
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        _password_error.value = false
        _password_rep_error.value = false
    }

    fun onPassword_RepChanged(password_rep: String) {
        _password_rep.value = password_rep
        _password_rep_error.value = false
    }

    fun onID_craftsmanChanged(ID_craftsman: String) {
        _ID_craftsman.value = ID_craftsman
        _craftsman_error.value = false
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidID_Craftsman(id: String): Boolean = id.matches(Regex("[a-zA-Z]{2}[0-9]{4}"))
    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    suspend fun onRegisterSelected(navController: NavController) {
        val email_correct = isValidEmail(email.value!!)
        _email_error.value = !email_correct
        val password_correct = isValidPassword(password.value!!)
        _password_error.value = !password_correct
        val ID_craftsman = isValidID_Craftsman(ID_craftsman.value!!)
        _craftsman_error.value = !ID_craftsman
        val password_iguales = password.value!! == password_rep.value!!
        _password_rep_error.value = !password_iguales

        val no_exist_user = notexistUser(email.value!!)

        if (no_exist_user) {
            if (craftsman.value!!) {
                if (email_correct && password_correct && ID_craftsman && password_iguales) {
                    val RegisterCorrect = createUser(email = email.value!!, password = password.value!!)
                    if (RegisterCorrect) {
                        navController.navigate(route = AppScreens.HomeScreen.route)
                    }
                }
            } else {
                if (email_correct && password_correct && password_iguales) {
                    val RegisterCorrect = createUser(email = email.value!!, password = password.value!!)
                    if (RegisterCorrect) {
                        val new_user: User = User(
                            name = user_name.value!!,
                            email = email.value!!,
                            image = "",
                            orders = ArrayList())
                        newUser(new_user)
                        navController.navigate(route = AppScreens.HomeScreen.route)
                    }
                }
            }
        } else {
            _ExistUser.value = true
        }


    }
}