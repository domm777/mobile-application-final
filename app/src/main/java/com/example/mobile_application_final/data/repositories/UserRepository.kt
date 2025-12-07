package com.example.mobile_application_final.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseAuth.getInstance()

    suspend fun register(email: String, password: String): FirebaseUser? {
        val result = db.createUserWithEmailAndPassword(email, password).await()
        return result.user
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val result = db.signInWithEmailAndPassword(email, password).await()
        return result.user
    }
}
