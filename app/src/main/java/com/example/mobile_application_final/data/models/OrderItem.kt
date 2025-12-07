package com.example.mobile_application_final.data.models

import com.google.firebase.firestore.DocumentId

data class OrderItem (
    @DocumentId
    val id: String = "",
    val address: String = "",
    val date_placed: String = "",
    val estimated_date: String = "",
    val status: String = "",
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val tracking_number: Long = 0,
    val user_id: String = "",
    val items: List<Item> = emptyList()
)

data class Item (
    val productId: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val quantity: Long = 0,
    val imgUrl: String = ""
)