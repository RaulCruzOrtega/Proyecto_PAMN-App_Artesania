package com.example.app_artesania.ui.createOrder

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.addImageOrderToFirebaseStorage
import com.example.app_artesania.data.addImageProductToFirebaseStorage
import com.example.app_artesania.data.newOrderDB
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.Offer
import com.example.app_artesania.model.newOrder
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.launch

class CreateOrderViewModel: ViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category
    private val _imageuri = MutableLiveData<Uri>()
    private val _imageselect = MutableLiveData<String>().apply { value = "" }
    val imageselect: LiveData<String> = _imageselect

    private val _titleError = MutableLiveData<Boolean>()
    val titleError: LiveData<Boolean> = _titleError
    private val _descriptionError = MutableLiveData<Boolean>()
    val descriptionError: LiveData<Boolean> = _descriptionError

    fun onCreateOrderChanged(title: String, description: String, category: Category) {
        _title.value = title
        _category.value = category
        _description.value = description
    }

    private fun isValidText(text: String): Boolean = text.isNotEmpty()
    private fun areFieldsValid(): Boolean {
        return isValidText(_title.value!!) && isValidText(_description.value!!)
    }

    fun imageselect(newImageUri: Uri){
        _imageuri.value = newImageUri
        _imageselect.value = newImageUri.lastPathSegment.toString()
        println(_imageuri.value)
    }

    fun createOrder(navController: NavController) {
        viewModelScope.launch {
            if (areFieldsValid()) {
                if (_imageuri.value != null) {
                    val uristorage = addImageOrderToFirebaseStorage(_imageuri.value!!, DataRepository.getUser()!!)
                    val newOrder: newOrder = newOrder(
                        title = title.value!!,
                        description = description.value!!,
                        category = category.value!!.categoryType.name,
                        userEmail = DataRepository.getUser()!!.email,
                        offers = ArrayList<Offer>(),
                        isAssigned = false,
                        image = uristorage.toString(),
                    )
                    newOrderDB(newOrder)
                }
                else{
                    val newOrder: newOrder = newOrder(
                        title = title.value!!,
                        description = description.value!!,
                        category = category.value!!.categoryType.name,
                        userEmail = DataRepository.getUser()!!.email,
                        offers = ArrayList<Offer>(),
                        isAssigned = false,
                        image = ""
                    )
                    newOrderDB(newOrder)
                }
                navController.navigate(route = AppScreens.OrdersScreen.route)
            } else {
                _titleError.value = !isValidText(_title.value!!)
                _descriptionError.value = !isValidText(_description.value!!)
            }
        }
    }
}