package com.example.app_artesania.ui.editProduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.addImageProductToFirebaseStorage
import com.example.app_artesania.data.getProduct
import com.example.app_artesania.data.modifyProduct
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.Category.Alfarería.getCategory
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.newProducto
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditProductViewModel (idProduct: String): ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    private val _imageuri = MutableLiveData<Uri>()
    private val _imageselect = MutableLiveData<String>()
    val imageselect: LiveData<String> = _imageselect
    private val _image = MutableLiveData<String>()
    private val _idProduct = MutableLiveData<String>()

    private val _nameError = MutableLiveData<Boolean>()
    val nameError: LiveData<Boolean> = _nameError
    private val _priceError = MutableLiveData<Boolean>()
    val priceError: LiveData<Boolean> = _priceError
    private val _descriptionError = MutableLiveData<Boolean>()
    val descriptionError: LiveData<Boolean> = _descriptionError

    init {
        _loadState.value = LoadState.LOADING
        _idProduct.value = idProduct
        loaddata(idProduct)
    }

    private fun loaddata(idProduct: String){
        viewModelScope.launch {
            _product.value = getProduct(idProduct)!!
            _name.value = _product.value!!.name
            _price.value = _product.value!!.price.toString()
            _description.value = _product.value!!.description
            _category.value = getCategory(_product.value!!.category)
            _image.value = _product.value!!.image
            delay(500)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun onEditProductChanged(name: String, price: String, category: Category, description: String) {
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

    fun imageselect(newImageUri: Uri){
        _imageuri.value = newImageUri
        _imageselect.value = newImageUri.lastPathSegment.toString()
        println(_imageuri.value)
    }

    private fun isValidText(text: String): Boolean = text.isNotEmpty()
    private fun areFieldsValid(): Boolean {
        return isValidText(_name.value!!) && isValidText(_price.value!!) && isValidText(_description.value!!)
    }

    fun editarProducto(navController: NavController){
        viewModelScope.launch {
            if (areFieldsValid()) {
                if (_imageuri.value != null) {
                    val uristorage = addImageProductToFirebaseStorage(
                        _imageuri.value!!,
                        DataRepository.getUser()!!
                    )
                    val producto_edit: newProducto = newProducto(
                        name = name.value!!,
                        image = uristorage.toString(),
                        price = price.value!!.toDouble(),
                        description = description.value!!,
                        category = category.value!!.categoryType.name,
                        idCraftsman = DataRepository.getUser()!!.idCraftsman,
                        uploadDate = _product.value!!.uploadDate
                    )
                    modifyProduct(producto_edit, _idProduct.value!!)
                } else {
                    val producto_edit: newProducto = newProducto(
                        name = name.value!!,
                        image = _image.value!!,
                        price = price.value!!.toDouble(),
                        description = description.value!!,
                        category = category.value!!.categoryType.name,
                        idCraftsman = DataRepository.getUser()!!.idCraftsman,
                        uploadDate = _product.value!!.uploadDate
                    )
                    modifyProduct(producto_edit, _idProduct.value!!)
                }
                if (navController.currentDestination?.route != AppScreens.ProductScreen.route + "/${_idProduct.value}") {
                    navController.navigate(route = AppScreens.ProductScreen.route + "/${_idProduct.value}")
                }
            } else {
            _nameError.value = !isValidText(_name.value!!)
            _priceError.value = !isValidText(_price.value!!)
            _descriptionError.value = !isValidText(_description.value!!)
            }
        }
    }

}