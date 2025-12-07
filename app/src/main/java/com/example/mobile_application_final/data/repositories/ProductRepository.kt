package com.example.mobile_application_final.data.repositories

import com.example.mobile_application_final.data.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    suspend fun getProducts(): List<Product> {
        return productsRef.get().await().documents.mapNotNull { it.toObject(Product::class.java) }
    }

    suspend fun updateStock(product: Product?, quantity: Long) {
        if (product == null) return

        val productRef = db.collection("products").document(product.id)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(productRef)
            val currentStock = snapshot.getLong("stock") ?: 0
            val newStock = maxOf(0, currentStock - quantity)

            transaction.update(productRef, "stock", newStock)
        }.await()
    }
}