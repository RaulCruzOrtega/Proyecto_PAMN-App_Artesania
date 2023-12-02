package com.example.app_artesania.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.deleteOrder
import com.example.app_artesania.data.getOrder
import com.example.app_artesania.data.getUser
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.launch

class OrderViewModel(orderId: String?, navController: NavController) : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState
    private val _order = MutableLiveData<Order?>()
    val order: MutableLiveData<Order?> = _order
    private val _userOrder = MutableLiveData<User?>()
    val userOrder: MutableLiveData<User?> = _userOrder
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: MutableLiveData<User?> = _currentUser

    init {
        _loadState.value = LoadState.LOADING
         loaddata(orderId!!, navController)
        println("inicia order")
    }

    private fun loaddata(orderId: String, navController: NavController) {
        if (navController.currentDestination?.route != AppScreens.OrdersScreen.route) {
            viewModelScope.launch {
                _order.value = getOrder(orderId)
                _currentUser.value = getUser(DataRepository.getUser()!!.email)
                _userOrder.value = getUser(_order.value!!.userEmail)
                _loadState.value = LoadState.SUCCESS
            }
        }
    }

    fun delOrder(navController: NavController){
        deleteOrder(_order.value!!.id)
        navController.navigate(route = AppScreens.OrdersScreen.route)
    }
}