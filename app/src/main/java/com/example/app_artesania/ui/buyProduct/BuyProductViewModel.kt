package com.example.app_artesania.ui.buyProduct

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class BuyProductViewModel : ViewModel() {

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
        return isCardNumberValid(cardNumber) && isExpiryDateValid(expiryDate) && isCvvValid(cvv)
    }
}
