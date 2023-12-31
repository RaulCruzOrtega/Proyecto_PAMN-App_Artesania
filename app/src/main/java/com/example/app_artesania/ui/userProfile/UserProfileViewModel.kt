package com.example.app_artesania.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.SingOut
import com.example.app_artesania.data.getProducts
import com.example.app_artesania.data.getProducts_Craftsman
import com.example.app_artesania.data.getPurchased
import com.example.app_artesania.data.getUser
import com.example.app_artesania.data.product_purchase
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel()  {

    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState

    private val _products = MutableLiveData<ArrayList<Product>?>()
    val products: LiveData<ArrayList<Product>?> = _products

    private val _products_purchased = MutableLiveData<ArrayList<Product>?>()
    val products_purchased: LiveData<ArrayList<Product>?> = _products_purchased

    private val _user = MutableLiveData<User>()
    val user: MutableLiveData<User> = _user

    private val _searchResults = MutableLiveData<ArrayList<Product>>()
    val searchResults: LiveData<ArrayList<Product>> = _searchResults

    private val _show_my_products = MutableLiveData<Boolean>().apply { value = false }
    val show_my_products: LiveData<Boolean> = _show_my_products

    private val _show_purchased = MutableLiveData<Boolean>().apply { value = false }
    val show_purchased: LiveData<Boolean> = _show_purchased

    init{
        _loadState.value = LoadState.LOADING
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _user.value = getUser(DataRepository.getUser()!!.email)!!
            if (DataRepository.getUser()!!.isCraftsman){
                _products.value = getProducts_Craftsman(DataRepository.getUser()!!.idCraftsman)
            }
            _products_purchased.value = getPurchased(_user.value!!.purchased)
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun change_show_my_products(){
        _show_my_products.value = !_show_my_products.value!!
    }

    fun change_show_purchased(){
        _show_purchased.value = !_show_purchased.value!!
    }

    fun cerrarSesión(navController: NavController){
        SingOut()
        navController.navigate(route = AppScreens.LoginScreen.route)
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val allProducts = getProducts()
            _searchResults.value = allProducts.filter {
                it.name.contains(query, ignoreCase = true)
            } as ArrayList<Product>
        }
    }

    fun delPurchased(){
        _products_purchased.value!!.clear()
        _show_purchased.value = !_show_purchased.value!!
        val user = DataRepository.getUser()!!
        user.purchased.clear()
        DataRepository.setUser(user)
        product_purchase(DataRepository.getUser()!!.email, DataRepository.getUser()!!.purchased)
    }

    fun resetSearch() {
        _searchResults.value = arrayListOf()
    }
}
