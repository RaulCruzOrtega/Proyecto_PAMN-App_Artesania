package com.example.app_artesania.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.getProductsFilterByCategory
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoryViewModel(category: String?, navController: NavController) : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState

    private val _products = MutableLiveData<ArrayList<Product>?>()
    val products: LiveData<ArrayList<Product>?> = _products
    val category: String = category!!

    init {
        _loadState.value = LoadState.LOADING
        viewModelScope.launch {
            _products.value = getProductsFilterByCategory(category!!)
            println("Products $category: " + products.value)
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
    }

}