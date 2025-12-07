package com.example.mobile_application_final.data.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.mobile_application_final.data.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

data class LoginValidationException(val msg: String) : IllegalStateException(msg)

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepository = UserRepository()

    var isLoading by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    suspend fun onAttemptLogin(): FirebaseUser {
        try {
            isLoading = true

            validateEmail(email)
            validatePassword(password)

            val user = userRepository.login(email, password)
            user?.let {
                return it
            }

            throw LoginValidationException("Login credentials are invalid or user doesn't exist.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            val message = e.message
            throw LoginValidationException(message ?: "Invalid login credentials")
        } catch (e: Exception) {
            // re-throw so it can still be caught, but our finally still runs
            throw e
        } finally {
            // always return loading back to false so this can be tried again
            isLoading = false
        }
    }

    private fun validateEmail(email: String) {
        if (email.isBlank()) {
            throw LoginValidationException("Email must be present")
        }
    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            throw LoginValidationException("Password must be present")
        }
    }

    fun onEmailChanged(username: String) {
        this.email = username
    }

    fun onPasswordChanged(password: String) {
        this.password = password
    }
}