package com.example.app_artesania.ui.createProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateProductViewModel: ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    fun onCreateProductChanged(name: String, price: String, description: String) {
        _name.value = name
        _price.value = price
        _description.value = description
    }
}