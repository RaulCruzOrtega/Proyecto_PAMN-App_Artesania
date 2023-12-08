package com.example.app_artesania.ui.infoCraftsman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.data.getCraftsman
import com.example.app_artesania.data.getProducts
import com.example.app_artesania.data.getProducts_Craftsman
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoCraftsmanViewModel(craftsmanId: String?): ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState

    private val _products = MutableLiveData<ArrayList<Product>?>()
    val products: LiveData<ArrayList<Product>?> = _products

    private val _craftsman = MutableLiveData<User?>()
    val craftsman: MutableLiveData<User?> = _craftsman

    private val _searchResults = MutableLiveData<ArrayList<Product>>()
    val searchResults: LiveData<ArrayList<Product>> = _searchResults


    init {
        _loadState.value = LoadState.LOADING
        if (craftsmanId != null) {
            loadData(craftsmanId)
        } else {
            _loadState.value = LoadState.ERROR
        }
    }

    private fun loadData(craftsmanId: String) {
        viewModelScope.launch {
            try {
                _craftsman.value = getCraftsman(craftsmanId)
                if (_craftsman.value?.isCraftsman == true) {
                    _products.value = getProducts_Craftsman(craftsmanId)
                }
                delay(500)
                _loadState.value = LoadState.SUCCESS
            } catch (e: Exception) {
                _loadState.value = LoadState.ERROR
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val allProducts = getProducts()
            _searchResults.value = allProducts.filter {
                it.name.contains(query, ignoreCase = true)
            } as ArrayList<Product>
        }
    }

    fun resetSearch() {
        _searchResults.value = arrayListOf()
    }
}



