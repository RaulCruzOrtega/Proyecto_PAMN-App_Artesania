package com.example.app_artesania.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.editUserName
import com.example.app_artesania.data.getUser
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    init{
        _loadState.value = LoadState.LOADING
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _user.value = getUser(DataRepository.getUser()!!.email)!!
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
    }
    fun onCreateProductChanged(userName: String) {
        _userName.value = userName
    }

    private fun isValidName(name: String?): Boolean = !name.isNullOrBlank()

    suspend fun editUserName(navController: NavController) {
        viewModelScope.launch {
            if (isValidName(userName.value)) {
                editUserName(user.value!!, userName.value!!)
                println(userName.value)
                navController.navigate(route = AppScreens.UserProfileScreen.route)
            }
        }
    }
}