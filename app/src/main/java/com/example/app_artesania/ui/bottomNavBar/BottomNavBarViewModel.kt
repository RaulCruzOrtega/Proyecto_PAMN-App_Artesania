package com.example.app_artesania.ui.bottomNavBar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.data.currentUserEmail
import com.example.app_artesania.data.getUser
import com.example.app_artesania.model.ItemsBottomNav
import com.example.app_artesania.model.User
import kotlinx.coroutines.launch

class BottomNavBarViewModel : ViewModel() {

    private val _user = MutableLiveData<User?>()

    private val _items = MutableLiveData<List<ItemsBottomNav>>()
    val items: LiveData<List<ItemsBottomNav>> = _items


    init {
        val email = currentUserEmail()
        viewModelScope.launch {
            _user.value = getUser(email!!)
            if (_user.value!!.isCraftsman){
                _items.value = listOf(
                    ItemsBottomNav.homeIcon,
                    ItemsBottomNav.favsIcon,
                    ItemsBottomNav.favs2Icon,
                    ItemsBottomNav.ordersIcon,
                    ItemsBottomNav.userIcon
                )
            } else {
                _items.value = listOf(
                    ItemsBottomNav.homeIcon,
                    ItemsBottomNav.favsIcon,
                    ItemsBottomNav.ordersIcon,
                    ItemsBottomNav.userIcon
                )
            }
        }
    }


}