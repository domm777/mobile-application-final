package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.mobile_application_final.data.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser

data class LoginValidationException(val msg: String) : IllegalStateException(msg)

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepository = UserRepository()

    var isLoading by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    suspend fun onAttemptLogin(): FirebaseUser? {
        // disallow repeat attempts
        if (isLoading) return null
        isLoading = true

        validateEmail(email)
        validatePassword(password)

        return try {
            userRepository.login(email, password)
        } catch (_: Exception) {
            null
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