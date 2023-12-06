package com.example.app_artesania.ui.buyProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_artesania.data.product_purchase
import com.example.app_artesania.model.DataRepository
import com.example.app_artesania.model.LoadState
import java.text.SimpleDateFormat
import java.util.*

class BuyProductViewModel(idProduct: String) : ViewModel() {

    private val _idProduct = MutableLiveData<String>()
    val idProduct: MutableLiveData<String> = _idProduct

    init {
        _idProduct.value = idProduct
    }

    fun isCardNumberValid(cardNumber: String): Boolean {
        if (cardNumber.length !in 13..19) return false
        return luhnCheck(cardNumber)
    }

    private fun luhnCheck(cardNumber: String): Boolean {
        var sum = 0
        var alternate = false
        for (i in cardNumber.length - 1 downTo 0) {
            var n = cardNumber[i].digitToInt()
            if (alternate) {
                n *= 2
                if (n > 9) {
                    n = (n % 10) + 1
                }
            }
            sum += n
            alternate = !alternate
        }
        return (sum % 10 == 0)
    }

    fun isExpiryDateValid(expiryDate: String): Boolean {
        if (!expiryDate.matches(Regex("\\d{2}/\\d{2}"))) return false
        val sdf = SimpleDateFormat("MM/yy", Locale.getDefault())
        sdf.isLenient = false
        return try {
            val expiry = sdf.parse(expiryDate)
            expiry.after(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun isCvvValid(cvv: String): Boolean {
        return cvv.length == 3 && cvv.all { it.isDigit() }
    }

    fun buyProduct(cardNumber: String, expiryDate: String, cvv: String): Boolean {
        if(isCardNumberValid(cardNumber) && isExpiryDateValid(expiryDate) && isCvvValid(cvv)){
            val user = DataRepository.getUser()!!
            user.purchased.add(_idProduct.value!!)
            DataRepository.setUser(user)
            product_purchase(DataRepository.getUser()!!.email, DataRepository.getUser()!!.purchased)
            return true
        }
        else{
            return false
        }
    }
}
