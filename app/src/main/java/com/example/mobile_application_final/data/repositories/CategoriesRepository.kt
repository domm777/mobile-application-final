package com.example.mobile_application_final.data.repositories

import com.example.mobile_application_final.data.models.Category
import com.example.mobile_application_final.data.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoriesRepository {
    private val db = FirebaseFirestore.getInstance()
    private val categoriesRef = db.collection("categories")

    suspend fun getCategories(): List<Category> {
        return categoriesRef.get().await().documents.mapNotNull { it.toObject(Category::class.java) }
    }
}