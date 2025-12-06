package com.example.mobile_application_final.data.models

import com.google.firebase.firestore.DocumentId

data class Product (
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",

    val price: Double = 0.0,
    val rating: Double = 0.0,
    val category: String = "",
    val image: String = "",
    val stock: Int = 0
)
