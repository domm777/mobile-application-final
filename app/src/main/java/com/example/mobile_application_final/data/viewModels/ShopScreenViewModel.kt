package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.Category
import com.example.mobile_application_final.data.models.Product
import com.example.mobile_application_final.data.repositories.CartDb
import com.example.mobile_application_final.data.repositories.CategoriesRepository
import com.example.mobile_application_final.data.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopScreenViewModel (application: Application) : AndroidViewModel(application) {
    private val prodRepository = ProductRepository()
    private val catRepository = CategoriesRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val dbHelper = CartDb(application)

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

    fun addToCart(product: Product){
        //check if there is stock
        if(product.stock <= 0)
            return
        val cartItem = dbHelper.getCartItem(product.id)
        //didn't get an item back
        if(cartItem.id == -1L){
            dbHelper.addCartItem(product.id)
        }else{
            //clamp updated quantity to max stock available
            val newQuantity = (cartItem.quantity + 1).coerceIn(0, product.stock)
            dbHelper.updateCartItem(cartItem.id, newQuantity)
        }
    }
}