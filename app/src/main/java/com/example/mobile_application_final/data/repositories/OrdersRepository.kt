package com.example.mobile_application_final.data.repositories

import com.example.mobile_application_final.data.models.OrderItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrdersRepository {
    private val db = FirebaseFirestore.getInstance()
    private val ordersRef = db.collection("orders")

    suspend fun getOrdersById(userId: String): List<OrderItem> {
        return ordersRef
            .whereEqualTo("user_id", userId)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(OrderItem::class.java) }
    }

    fun addOrder(order: OrderItem){
        db.collection("orders").add(order)
    }
}