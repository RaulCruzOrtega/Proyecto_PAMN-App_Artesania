package com.example.app_artesania.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.data.getAllOrders
import com.example.app_artesania.data.getOrdersByEmail
import com.example.app_artesania.data.getUser
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import kotlinx.coroutines.launch

class OrdersViewModel  : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState
    private val _myOrders = MutableLiveData<ArrayList<Order>?>()
    val myOrders: LiveData<ArrayList<Order>?> = _myOrders
    private val _allOrders = MutableLiveData<ArrayList<Order>?>()
    val allOrders: LiveData<ArrayList<Order>?> = _allOrders
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user
    private val _users = MutableLiveData<ArrayList<User>?>()
    val users: LiveData<ArrayList<User>?> = _users.apply { value = ArrayList() }

    init{
        _loadState.value = LoadState.LOADING
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _user.value = getUser(DataRepository.getUser()!!.email)
            _myOrders.value = getOrdersByEmail(_user.value!!.email)
            if (DataRepository.getUser()!!.isCraftsman) {
                _allOrders.value = getAllOrders(_user.value!!.email)
                val userList = mutableListOf<User>()
                for (order in _allOrders.value.orEmpty()) {
                    if(order.userEmail != _user.value!!.email) {
                        userList.add(getUser(order.userEmail))
                    }
                }
                _users.value = ArrayList(userList)
            }
            _loadState.value = LoadState.SUCCESS
        }
    }
}