package com.example.app_artesania.ui.favouriteProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.data.existProduct
import com.example.app_artesania.data.getProduct
import com.example.app_artesania.data.getProducts
import com.example.app_artesania.data.modifyUserFavo
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavouriteProductsViewModel: ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState

    private val _favoProducts = MutableLiveData<ArrayList<Product>>()
    val favoProducts: LiveData<ArrayList<Product>> = _favoProducts
    private val _searchResults = MutableLiveData<ArrayList<Product>>()
    val searchResults: LiveData<ArrayList<Product>> = _searchResults

    init{
        _loadState.value = LoadState.LOADING
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch{
            val favproducts = DataRepository.getUser()!!.favoproducts
            val arrayproducts = ArrayList<Product>()
            val array_favo_elimi = ArrayList<String>()
            for (favproduct in favproducts){
                if(existProduct(favproduct)){
                    val product = getProduct(favproduct)
                    arrayproducts.add(product)
                } else {
                    array_favo_elimi.add(favproduct)
                }
            }
            val user = DataRepository.getUser()!!
            for (eliminar in array_favo_elimi){
                user.favoproducts.remove(eliminar)
            }
            DataRepository.setUser(user)
            modifyUserFavo(DataRepository.getUser()!!.email,DataRepository.getUser()!!.favoproducts)
            _favoProducts.value = arrayproducts
            delay(500)
            _loadState.value = LoadState.SUCCESS
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