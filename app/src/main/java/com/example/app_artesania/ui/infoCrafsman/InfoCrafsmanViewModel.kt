package com.example.app_artesania.ui.infoCrafsman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.SingOut
import com.example.app_artesania.data.getCraftsman
import com.example.app_artesania.data.getCraftsmans
import com.example.app_artesania.data.getProducts
import com.example.app_artesania.data.getProducts_Craftsman
import com.example.app_artesania.data.getUser
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoCrafsmanViewModel(private val craftsmanId: String?, navController: NavController): ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState

    private val _products = MutableLiveData<ArrayList<Product>?>()
    val products: LiveData<ArrayList<Product>?> = _products

    private val _craftsman = MutableLiveData<User?>()
    val craftsman: MutableLiveData<User?> = _craftsman

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
                // Obtener la información del artesano
                _craftsman.value = getCraftsman(craftsmanId)

                // Si el usuario es un artesano, cargar sus productos
                if (_craftsman.value?.isCraftsman == true) {
                    _products.value = getProducts_Craftsman(craftsmanId)
                }

                _loadState.value = LoadState.SUCCESS
            } catch (e: Exception) {
                _loadState.value = LoadState.ERROR
                // Log the error or handle it as needed
            }
        }
    }
}



