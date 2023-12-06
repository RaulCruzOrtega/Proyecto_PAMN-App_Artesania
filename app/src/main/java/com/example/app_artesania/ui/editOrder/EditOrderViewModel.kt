package com.example.app_artesania.ui.editOrder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.getOrder
import com.example.app_artesania.data.modifyOrder
import com.example.app_artesania.model.Category
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Order
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.launch

class EditOrderViewModel (idOrder: String?): ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState
    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order
    private val _idOrder = MutableLiveData<String>()
    val idOrder: LiveData<String> = _idOrder
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category


    private val _titleError = MutableLiveData<Boolean>()
    val titleError: LiveData<Boolean> = _titleError
    private val _descriptionError = MutableLiveData<Boolean>()
    val descriptionError: LiveData<Boolean> = _descriptionError

    init {
        _loadState.value = LoadState.LOADING
        _idOrder.value = idOrder!!
        loaddata(idOrder)
    }

    private fun loaddata(idOrder: String){
        viewModelScope.launch {
            _order.value = getOrder(idOrder)!!
            _title.value = _order.value!!.title
            _description.value = _order.value!!.description
            _category.value = Category.Otro.getCategory(_order.value!!.category)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun onEditOrderChanged(title: String, description: String, category: Category) {
        _title.value = title
        _category.value = category
        _description.value = description
    }

    private fun isValidText(text: String): Boolean = text.isNotEmpty()

    private fun areFieldsValid(): Boolean {
        return isValidText(_title.value!!) && isValidText(_description.value!!)
    }

    fun editOrder(navController: NavController){
        viewModelScope.launch {
            if (areFieldsValid()) {
                val editedOrder: Order = Order(
                    id = idOrder.value!!,
                    title = title.value!!,
                    description = description.value!!,
                    category = category.value!!.categoryType.name,
                    userEmail = DataRepository.getUser()!!.email,
                    offers = _order.value!!.offers,
                    isAssigned = false
                )
                modifyOrder(editedOrder)
                navController.navigate(route = AppScreens.OrderScreen.route + "/${_idOrder.value}")
            } else {
                _titleError.value = !isValidText(_title.value!!)
                _descriptionError.value = !isValidText(_description.value!!)
            }
        }
    }

}