package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.OrderItem
import com.example.mobile_application_final.data.repositories.OrdersRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderScreenViewModel (application: Application) : AndroidViewModel(application) {
    private val orderRepository = OrdersRepository()
    private val _orders = MutableStateFlow<List<OrderItem>>(emptyList())
    val orders: StateFlow<List<OrderItem>> = _orders
    private var ordersList: List<OrderItem> = emptyList()

    private val _selectedOrder = MutableStateFlow<OrderItem?>(null)
    val selectedOrder = _selectedOrder.asStateFlow()

    fun selectOrder(order: OrderItem) {
        _selectedOrder.value = order
    }

    init{
        loadOrders()
    }

    fun loadOrders(){
        //get user Id
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""

        viewModelScope.launch {
            _orders.value = orderRepository.getOrdersById(userId)
            ordersList = _orders.value
        }
    }


}