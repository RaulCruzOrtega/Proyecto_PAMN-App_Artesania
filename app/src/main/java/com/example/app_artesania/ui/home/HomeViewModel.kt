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
    val loadState: MutableLiveData<LoadState> = _loadState

    private val _craftsmansDB = MutableLiveData<ArrayList<User>?>()
    val craftsmansDB: LiveData<ArrayList<User>?> = _craftsmansDB

    private val _productsDB = MutableLiveData<ArrayList<Product>?>()
    val productsDB: LiveData<ArrayList<Product>?> = _productsDB

    private val _categories = MutableLiveData<ArrayList<Category>>()
    val categories: LiveData<ArrayList<Category>> = _categories

    init {
        lanzamiento()
        println("OTRAVEZ")
    }

    private fun lanzamiento() {
        try {
            _loadState.value = LoadState.LOADING
            loadCategories()
            loadData()

        }
        catch (e: Exception){
            println("Error ${e.message}")
            _loadState.value = LoadState.ERROR
        }
    }

    private fun loadCategories() {
        _categories.value = getCategories()
    }

    private fun loadData() {
        try {
            viewModelScope.launch {
                _craftsmansDB.value = getCraftsmans()
                _productsDB.value = getProducts()
                println(craftsmansDB.value)
                println("Products: " + productsDB.value)
                delay(500)
                _loadState.value = LoadState.SUCCESS
            }
        }
        catch (e: Exception){
            println("Error load craftmans")
        }
    }
}