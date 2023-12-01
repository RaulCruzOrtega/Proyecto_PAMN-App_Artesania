package com.example.app_artesania.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.model.LoadState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrdersViewModel  : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState

    init{
        _loadState.value = LoadState.LOADING
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
    }
}