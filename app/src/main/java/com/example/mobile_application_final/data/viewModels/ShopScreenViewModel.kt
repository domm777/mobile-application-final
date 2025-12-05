package com.example.mobile_application_final.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.Category
import com.example.mobile_application_final.data.models.Product
import com.example.mobile_application_final.data.repositories.CategoriesRepository
import com.example.mobile_application_final.data.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopScreenViewModel : ViewModel() {
    private val prodRepository = ProductRepository()
    private val catRepository = CategoriesRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init{
        loadCategories()
        loadProducts()
    }

    fun loadProducts(){
        viewModelScope.launch {
            _products.value = prodRepository.getProducts()
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = catRepository.getCategories()
        }
    }

    fun seedDbBtn(){
        prodRepository.seedDb()
    }
}