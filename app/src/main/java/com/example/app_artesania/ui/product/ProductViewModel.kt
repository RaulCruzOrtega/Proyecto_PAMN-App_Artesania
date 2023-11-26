package com.example.app_artesania.ui.product


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.data.TemporalDatabase
import com.example.app_artesania.data.getCraftsman
import com.example.app_artesania.data.getProduct
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProductViewModel(productId: String?) : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _craftsman = MutableLiveData<User>()
    val craftsman: LiveData<User> = _craftsman

    init {
        _loadState.value = LoadState.LOADING
        loaddata(productId!!)
    }

    private fun loaddata(productId: String) {
        viewModelScope.launch {
            _product.value = getProduct(productId)!!
            _craftsman.value = getCraftsman(_product.value!!.idCraftsman)!!
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
    }
}

