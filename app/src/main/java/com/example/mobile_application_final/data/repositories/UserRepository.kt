package com.example.mobile_application_final.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseAuth.getInstance()

    suspend fun register(displayName: String, email: String, password: String): FirebaseUser? {
        val result = db.createUserWithEmailAndPassword(email, password).await()

        return if (result.user != null) {
            // if we get a user back, we will set their display name to the provided value
            result.user!!.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
            )

            return result.user
        } else {
            null
        }
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val result = db.signInWithEmailAndPassword(email, password).await()
        return result.user
    }
}
