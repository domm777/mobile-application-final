package com.example.mobile_application_final.data.repositories

import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.models.Product
import com.example.mobile_application_final.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    suspend fun getUsers(): List<User> {
        return usersRef.get().await().documents.mapNotNull { it.toObject(User::class.java) }
    }

    fun seedDb(){
        items.forEach { item ->
            db.collection("users")
                .add(item)
        }
    }

    fun User.toMap(): Map<String, Any> {
        return mapOf(
            // email, username, password
            "email" to email,
            "username" to username,
            "password" to password
        )
    }

    val items = listOf(
        // -------------------- Pokémon --------------------
        User(
            email = "user@gmail.com",
            username = "user",
            password = "password123"
        )
    )
}