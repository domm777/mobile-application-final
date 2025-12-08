package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.Item
import com.example.mobile_application_final.data.models.OrderItem
import com.example.mobile_application_final.data.repositories.OrdersRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailsScreenViewModel(application: Application) : AndroidViewModel(application){
    private val orderRepository = OrdersRepository()
    private val _order = MutableStateFlow<OrderItem>(OrderItem())
    val order: StateFlow<OrderItem> = _order
    private var orderProducts: List<Item> = emptyList()



    init{

    }

    fun loadcurrentOrder(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""

        viewModelScope.launch {
            _order.value = orderRepository.getOrderItemById(userId)
            orderProducts = _order.value.items
        }
    }
}