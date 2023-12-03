package com.example.app_artesania.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.SingOut
import com.example.app_artesania.data.getProducts
import com.example.app_artesania.data.getProducts_Craftsman
import com.example.app_artesania.data.getUser
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

    private val _user = MutableLiveData<User>()
    val user: MutableLiveData<User> = _user

    private val _searchResults = MutableLiveData<ArrayList<Product>>()
    val searchResults: LiveData<ArrayList<Product>> = _searchResults
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
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
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

    fun resetSearch() {
        _searchResults.value = arrayListOf() // Restablecer los resultados de búsqueda
    }
}
