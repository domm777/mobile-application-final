package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.CartItem
import com.example.mobile_application_final.data.models.Product
import com.example.mobile_application_final.data.repositories.CartDb
import com.example.mobile_application_final.data.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.collections.firstOrNull
import kotlin.math.min

class CartScreenViewModel (application: Application) : AndroidViewModel(application) {
    private val dbHelper = CartDb(application)
    private val prodRepository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    private var productList: List<Product> = emptyList()

    var subtotal by mutableStateOf(0.0)
        private set
    var tax by mutableStateOf(0.0)
        private set
    var total by mutableStateOf(0.0)
        private set


    var cartItems = mutableStateListOf<CartItem>()
        private set

    init{
        loadProducts()
        updateList()
    }

    fun updateList (){
        cartItems.clear()
        cartItems.addAll(dbHelper.getCartItems())
        updateValues()
    }

    fun updateValues(){
        print("Updating values")
        subtotal = cartItems.sumOf {
            (productList.firstOrNull { product -> product.id == it.itemId }?.price ?: 0.0) * it.quantity
        }
        tax = 0.13 * subtotal
        total = subtotal + tax
    }

    fun loadProducts(){
        viewModelScope.launch {
            _products.value = prodRepository.getProducts()
            productList = _products.value
            updateValues()
        }
    }

    fun removeItem(cartItem: CartItem){
        dbHelper.deleteCartItem(cartItem.id)
        updateList()
    }

    fun updateItem(cartItem: CartItem, product: Product, value: Long){
        if(value < 0){
            if(cartItem.quantity + value <= 0)
                removeItem(cartItem)
            else
                dbHelper.updateCartItem(cartItem.id, cartItem.quantity + value)
        }else{
            dbHelper.updateCartItem(cartItem.id, min(cartItem.quantity + value, product.stock))
        }
        updateList()
    }


}