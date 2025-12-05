package com.example.mobile_application_final.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.models.Product
import com.example.mobile_application_final.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopScreenViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init{
        loadProducts()
    }

    fun loadProducts(){
        viewModelScope.launch {
            _products.value = repository.getProducts()
        }
    }
}