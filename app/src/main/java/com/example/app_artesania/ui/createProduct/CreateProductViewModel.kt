package com.example.app_artesania.ui.createProduct

import android.media.MediaRouter.RouteCategory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.newProduct
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.newProducto
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.launch

class CreateProductViewModel: ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    fun onCreateProductChanged(name: String, price: String, category: Category, description: String) {
        _name.value = name
        _price.value = filterPriceInput(price)
        _category.value = category
        _description.value = description
    }

    private fun filterPriceInput(input: String): String {
        return input.filterIndexed { index, char ->
            if (char == '.') {
                // Permitir solo un punto decimal
                input.indexOf(char) == index
            } else {
                // Permitir solo dígitos
                char.isDigit()
            }
        }
    }

    fun crearProducto(navController: NavController){
        val new_producto: newProducto = newProducto(
            name = name.value!!,
            image = "",
            price = price.value!!.toDouble(),
            description = description.value!!,
            category = category.value!!.categoryType.name,
            idCraftsman = DataRepository.getUser()!!.idCraftsman
        )
        viewModelScope.launch { newProduct(new_producto) }
        _name.value = ""
        _price.value = ""
        _category.value = Category.Alfarería
        _description.value = ""
        navController.navigate(route = AppScreens.UserProfileScreen.route)
    }

}