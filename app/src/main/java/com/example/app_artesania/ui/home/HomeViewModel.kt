package com.example.app_artesania.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_artesania.R
import com.example.app_artesania.data.TemporalDatabase
import com.example.app_artesania.data.getCraftsmans
import com.example.app_artesania.model.Product
import com.example.app_artesania.model.User
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _craftsmansDB = MutableLiveData<List<User>?>()
    val craftsmansDB: MutableLiveData<List<User>?> = _craftsmansDB


    init {
        loadCraftmans()
    }

    private fun loadCraftmans() {
        try {
            viewModelScope.launch {
                _craftsmansDB.value = getCraftsmans()
                println(craftsmansDB.value)
            }
        }
        catch (e: Exception){
            println("Error")
        }
    }

    data class Category(
        val name: String,
        val img: Int
    )

    val categories = ArrayList(
        listOf(
            Category("Textiles", R.drawable.ropa),
            Category("Cerámica", R.drawable.ceramica),
            Category("Esculturas", R.drawable.escultura),
            Category("Joyas", R.drawable.joyas),
            Category("Juguetes", R.drawable.juguete),
            Category("Cestería", R.drawable.cesta)
        )
    )

    val products = TemporalDatabase.products

    val products2 = ArrayList(
        listOf(
            Product.p6, Product.p5, Product.p2, Product.p4
        )
    )

    val craftsmans = ArrayList(
        listOf(
            User.a1, User.a2, User.a3, User.a4, User.a5
        )
    )

}