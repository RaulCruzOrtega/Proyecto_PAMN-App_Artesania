package com.example.app_artesania.ui.editProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.addImageProductToFirebaseStorage
import com.example.app_artesania.data.addImageUserToFirebaseStorage
import com.example.app_artesania.data.modifyUserName
import com.example.app_artesania.data.getUser
import com.example.app_artesania.data.modifyUserImage
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

    private val _imageuri = MutableLiveData<Uri>()
    private val _imageselect = MutableLiveData<String>()
    val imageselect: LiveData<String> = _imageselect

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
    private fun isValidImage(image: String?): Boolean = !image.isNullOrBlank()

    suspend fun editUserName(navController: NavController) {
        viewModelScope.launch {
            if (isValidName(userName.value)) {
                modifyUserName(user.value!!, userName.value!!)
                println(userName.value)
                navController.navigate(route = AppScreens.UserProfileScreen.route)
            }
        }
    }

    fun imageselect(newImageUri: Uri){
        _imageuri.value = newImageUri
        _imageselect.value = newImageUri.lastPathSegment.toString()
        println(_imageuri.value)
    }

    suspend fun editUserImage(navController: NavController) {
        viewModelScope.launch {
            if (isValidImage(_imageselect.value)) {
                val uriStorage: Uri= addImageUserToFirebaseStorage(_imageuri.value!!, DataRepository.getUser()!!)
                modifyUserImage(user.value!!, uriStorage)
                navController.navigate(route = AppScreens.UserProfileScreen.route)
            }
        }
    }
}