package com.example.app_artesania.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_artesania.data.deleteOrder
import com.example.app_artesania.data.getCraftsman
import com.example.app_artesania.data.getOrder
import com.example.app_artesania.data.getUser
import com.example.app_artesania.data.modifyOrder
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import com.example.app_artesania.model.Offer
import com.example.app_artesania.model.Order
import com.example.app_artesania.model.User
import com.example.app_artesania.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderViewModel(orderId: String?, navController: NavController) : ViewModel() {
    private val _loadState = MutableLiveData<LoadState>()
    val loadState: MutableLiveData<LoadState> = _loadState
    private val _order = MutableLiveData<Order?>()
    val order: MutableLiveData<Order?> = _order
    private val _userOrder = MutableLiveData<User>()
    val userOrder: MutableLiveData<User> = _userOrder
    private val _currentUser = MutableLiveData<User>()
    val currentUser: MutableLiveData<User> = _currentUser
    private val _offerPrice = MutableLiveData<String?>()
    val offerPrice: MutableLiveData<String?> = _offerPrice
    private val _offerComments = MutableLiveData<String?>()
    val offerComments: MutableLiveData<String?> = _offerComments
    private val _offerPriceError = MutableLiveData<Boolean?>()
    val offerPriceError: MutableLiveData<Boolean?> = _offerPriceError
    private val _offerCommentsError = MutableLiveData<Boolean?>()
    val offerCommentsError: MutableLiveData<Boolean?> = _offerCommentsError
    private val _offeredCraftsmans = MutableLiveData<ArrayList<User>>()
    val offeredCraftsmans: MutableLiveData<ArrayList<User>> = _offeredCraftsmans
    private val _editingOffer = MutableLiveData<Boolean>()
    val editingOffer: MutableLiveData<Boolean> = _editingOffer

    init {
        _loadState.value = LoadState.LOADING
        loaddata(orderId!!, navController)
    }

    private fun loaddata(orderId: String, navController: NavController) {
        if (navController.currentDestination?.route != AppScreens.OrdersScreen.route) {
            viewModelScope.launch {
                _order.value = getOrder(orderId)
                _currentUser.value = getUser(DataRepository.getUser()!!.email)!!
                _userOrder.value = getUser(_order.value!!.userEmail)!!
                val craftmansList = mutableListOf<User>()
                for (offer in _order.value!!.offers) {
                    craftmansList.add(getCraftsman(offer.idCraftsman))
                }
                _offeredCraftsmans.value = ArrayList(craftmansList)
                delay(500)
                _loadState.value = LoadState.SUCCESS
            }
        }
    }

    fun delOrder(navController: NavController) {
        deleteOrder(_order.value!!.id)
        navController.navigate(route = AppScreens.OrdersScreen.route)
    }

    fun editOrder(navController: NavController) {
        navController.navigate(route = AppScreens.EditOrderScreen.route + "/${_order.value!!.id}")
    }

    fun onEditOfferChanged(offerPrice: String, offerComments: String) {
        _offerPrice.value = filterPriceInput(offerPrice)
        _offerComments.value = offerComments
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

    private fun isValidText(text: String?): Boolean = !text.isNullOrBlank()

    fun makeOffer(navController: NavController) {
        _editingOffer.value = true
        if (isValidText(_offerPrice.value) && isValidText(_offerComments.value)) {
            val offer = Offer(
                DataRepository.getUser()!!.idCraftsman,
                _offerPrice.value!!.toDouble(),
                _offerComments.value!!
            )
            _order.value!!.offers.add(offer)
            viewModelScope.launch {
                modifyOrder(_order.value!!)
            }
            navController.navigate(route = AppScreens.OrderScreen.route + "/${_order.value!!.id}")
        }
    }

    fun deleteOffer(order: Order, idCraftsman: String, navController: NavController){
        val newOffersList: ArrayList<Offer> = ArrayList(order.offers.filter { it.idCraftsman != idCraftsman })
        viewModelScope.launch {
            val editedOrder: Order = Order(
                id = order.id,
                title = order.title,
                description = order.description,
                category = order.category,
                userEmail = order.userEmail,
                offers = newOffersList,
                isAssigned = order.isAssigned
            )
            modifyOrder(editedOrder)
            navController.navigate(route = AppScreens.OrderScreen.route + "/${order.id}")
        }
    }

    fun editOffer(order: Order, idCraftsman: String, navController: NavController){
        deleteOffer(order, idCraftsman, navController)
        viewModelScope.launch {
            _order.value = getOrder(order.id)
            makeOffer(navController)
        }
    }

    fun isEditingOffer(offer: Offer): Boolean {
        _offerPrice.value = offer.price.toString()
        _offerComments.value = offer.comment
        _editingOffer.value = true
        return true
    }

    fun acceptOffer(order: Order, idCraftsman: String, navController: NavController){
        val newOffersList: ArrayList<Offer> = ArrayList(order.offers.filter { it.idCraftsman == idCraftsman })
        viewModelScope.launch {
            val editedOrder: Order = Order(
                id = order.id,
                title = order.title,
                description = order.description,
                category = order.category,
                userEmail = order.userEmail,
                offers = newOffersList,
                isAssigned = true
            )
            modifyOrder(editedOrder)
            navController.navigate(route = AppScreens.OrderScreen.route + "/${order.id}")
        }
    }
}