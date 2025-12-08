package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.CartItem
import com.example.mobile_application_final.data.models.Item
import com.example.mobile_application_final.data.models.OrderItem
import com.example.mobile_application_final.data.models.Product
import com.example.mobile_application_final.data.repositories.CartDb
import com.example.mobile_application_final.data.repositories.OrdersRepository
import com.example.mobile_application_final.data.repositories.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random
import kotlin.collections.firstOrNull
import kotlin.math.min

class CartScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = CartDb(application)
    private val prodRepository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    private var productList: List<Product> = emptyList()

    private val orderRepository = OrdersRepository()

    var subtotal by mutableStateOf(0.0)
        private set
    var tax by mutableStateOf(0.0)
        private set
    var total by mutableStateOf(0.0)
        private set


    var cartItems = mutableStateListOf<CartItem>()
        private set

    init {
        loadProducts()
        updateList()
    }

    fun updateList() {
        cartItems.clear()
        cartItems.addAll(dbHelper.getCartItems())
        updateValues()
    }

    fun updateValues() {
        subtotal = cartItems.sumOf {
            (productList.firstOrNull { product -> product.id == it.itemId }?.price
                ?: 0.0) * it.quantity
        }
        tax = 0.13 * subtotal
        total = subtotal + tax
    }

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = prodRepository.getProducts()
            productList = _products.value
            updateValues()
        }
    }

    fun removeItem(cartItem: CartItem) {
        dbHelper.deleteCartItem(cartItem.id)
        updateList()
    }

    fun updateItem(cartItem: CartItem, product: Product?, value: Long) {
        if (product == null)
            return
        if (value < 0) {
            if (cartItem.quantity + value <= 0)
                removeItem(cartItem)
            else
                dbHelper.updateCartItem(cartItem.id, cartItem.quantity + value)
        } else {
            dbHelper.updateCartItem(cartItem.id, min(cartItem.quantity + value, product.stock))
        }
        updateList()
    }

    fun checkOut() {
        val sub = subtotal
        val tx = tax
        val tot = total


        // Early return for empty cart
        if (cartItems.size <= 0)
            return

        // Check if all items have sufficient stock
        cartItems.forEach { item ->
            val prod = productList.firstOrNull { prod -> prod.id == item.itemId }
            if (item.quantity > (prod?.stock ?: 0)) {
                return
            }
        }

        viewModelScope.launch {
            //update stock
            cartItems.forEach { item ->
                val prod = productList.firstOrNull { prod -> prod.id == item.itemId }
                prodRepository.updateStock(prod, item.quantity)
            }

            //get random date in the future
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, (3..10).random())

            //dateing format
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

            //get user Id
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid ?: ""

            //create new order
            val newOrder = OrderItem(
                address = "Random Address",
                date_placed = dateFormat.format(Date()),
                estimated_date = dateFormat.format(calendar.time),
                status = "Pending",
                subtotal = sub,
                tax = tx,
                total = tot,
                tracking_number = Random.nextLong(100000000000L, 999999999999L),
                //put firebase ID of user here
                user_id = userId,
                items = cartItems.map { cartItem ->
                    val prod = productList.firstOrNull { prod -> prod.id == cartItem.itemId }
                    Item(
                        productId = cartItem.itemId,
                        name = prod?.name ?: "",
                        description = prod?.description ?: "",
                        price = prod?.price ?: 0.0,
                        quantity = cartItem.quantity,
                        imgUrl = prod?.image ?: ""
                    )
                }
            )

            //add new order
            orderRepository.addOrder(newOrder)

            //clear cart
            withContext(Dispatchers.Main) {
                dbHelper.clearCart()
                updateList()
            }
        }
    }
}