package com.example.app_artesania.ui.bottomNavBar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.ItemsBottomNav
import com.example.app_artesania.model.User
import kotlinx.coroutines.launch

class BottomNavBarViewModel : ViewModel() {

    private val _user = MutableLiveData<User?>()

    private val _items = MutableLiveData<List<ItemsBottomNav>>()
    val items: LiveData<List<ItemsBottomNav>> = _items


    init {
        viewModelScope.launch {
            val user = DataRepository.getUser()
            if (user!!.isCraftsman){
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