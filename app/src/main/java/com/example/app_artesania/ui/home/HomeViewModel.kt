package com.example.app_artesania.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import com.example.app_artesania.data.getCraftsmans
import com.example.app_artesania.data.getProducts
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.Category.Alfarería.getCategories
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState

    private val _craftsmansDB = MutableLiveData<ArrayList<User>?>()
    val craftsmansDB: LiveData<ArrayList<User>?> = _craftsmansDB

    private val _productsDB = MutableLiveData<ArrayList<Product>?>()
    val productsDB: LiveData<ArrayList<Product>?> = _productsDB

    private val _categories = MutableLiveData<ArrayList<Category>>()
    val categories: LiveData<ArrayList<Category>> = _categories

    private val _searchResults = MutableLiveData<ArrayList<Product>>()
    val searchResults: LiveData<ArrayList<Product>> = _searchResults

    init {
        lanzamiento()
    }

    private fun lanzamiento() {
        try {
            _loadState.value = LoadState.LOADING
            loadCategories()
            loadData()
        }
        catch (e: Exception) {
            println("Error ${e.message}")
            _loadState.value = LoadState.ERROR
        }
    }

    private fun loadCategories() {

        _categories.value = getCategories()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _craftsmansDB.value = getCraftsmans()
                _productsDB.value = getProducts()
                delay(500)
                _loadState.value = LoadState.SUCCESS
            } catch (e: Exception) {
                _loadState.value = LoadState.ERROR
                println("Error load craftmans: ${e.message}")
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
        _searchResults.value = arrayListOf() // Restablecer los resultados de búsqueda
    }

}
