package com.example.mobile_application_final.models

data class Product (
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val category: String = ""
)